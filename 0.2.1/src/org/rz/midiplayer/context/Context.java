
package org.rz.midiplayer.context;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.logging.Level;
import javax.sound.midi.MidiDevice;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import org.rz.midiplayer.AppConstants;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.midi.MIDIDeviceManager;
import org.rz.midiplayer.midi.MidiEventListener;
import org.rz.midiplayer.plugin.PluginManager;
import org.rz.midiplayer.plugin.renderer.RendererPlugin;
import org.rz.midiplayer.xmlmodule.JAXBUtil;
import org.rz.midiplayer.xmlmodule.XSDConstants;
import org.rz.midiplayer.xmlmodule.appconfig.ApplicationConfig;
import org.rz.midiplayer.xmlmodule.midispec.Spec;
import org.xml.sax.SAXException;

/**
 *
 * @author rz
 */
public class Context implements Loggable, MidiEventListener
{
    /** アプリケーション設定ファイル内容の格納 */
    private ApplicationConfig config;
    private JAXBElement<ApplicationConfig> configElement;

    /** デバイス定義ファイル内容の格納 */
    private Device device = new Device( AppConstants.DEVICE_DEF_DIR );

    /** MIDI 再生処理本体 */
    private Player player;

    /** MIDI イベントの通知先 */
    private final ArrayList<MidiEventHandler> midiEventHandlers = new ArrayList<MidiEventHandler>();

    /** MIDI 規格毎の定義ファイル格納(ハッシュ) */
    private final Hashtable <String, Spec> midiSpecTable = new Hashtable<String, Spec>();

    /** MIDI 規格毎の定義ファイル格納(配列) */
    private final ArrayList<Spec> midiSpecArray = new ArrayList<Spec>();

    /***/
    private PluginManager<RendererPlugin> rendererPluginManager;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Context インスタンスを生成する。
     */
    public Context() throws JAXBException, SAXException, ClassNotFoundException, IOException
    {
        initialize();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void initialize() throws JAXBException, SAXException, ClassNotFoundException, IOException
    {
        initializeAppConfig();
        initializeMidiSpec();
        loadDeviceFile();

        rendererPluginManager = new PluginManager<RendererPlugin>( RendererPlugin.class, AppConstants.PLUGIN_RENDERER_DIR );

        player = new Player();
        player.setMidiEventLister( this );
        setMidiOut( config.getMidiout().getName() );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void initializeAppConfig()
    {
        JAXBUtil<ApplicationConfig> appConfUtil = new JAXBUtil<ApplicationConfig>( ApplicationConfig.class );

        try
        {
            configElement = appConfUtil.loadFromFile( XSDConstants.APPCONFIG_XSD_URI, AppConstants.APP_CONFIG_FILE );
            config        = configElement.getValue();
            logger.info( "config file loaded." );
        }
        catch( Exception e )
        {
            logger.log( Level.SEVERE, "Failed to loading Application Config file.", e );
            logger.warning( "try to using the Default Config : " + AppConstants.DEFAULT_APPCONFIG_RES );
            try
            {
                configElement = appConfUtil.loadFromClasspath( XSDConstants.APPCONFIG_XSD_URI, AppConstants.DEFAULT_APPCONFIG_RES );
                config        = configElement.getValue();
                logger.warning( "OK. Using the Default Config : " + AppConstants.DEFAULT_APPCONFIG_RES + ":" + config );
            }
            catch( Exception ee )
            {
                logger.log( Level.SEVERE, "FATAL ERROR", ee );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void initializeMidiSpec()
    {
        JAXBUtil<Spec> midiSpecUtil = new JAXBUtil<Spec>( Spec.class );

        logger.info( "Loding MIDI Specification files..." );

        try
        {
            File dir = new File( AppConstants.MIDI_DEF_DIR );
            File[] files = dir.listFiles( new FileFilter() {
                @Override
                public boolean accept( File p )
                {
                    return p.isFile() && p.getName().endsWith( ".xml" );
                }
            });

            midiSpecTable.clear();
            midiSpecArray.clear();

            for( File f : files )
            {
                logger.info( f.getName() );
                Spec p = midiSpecUtil.loadFromFile( XSDConstants.MIDISPEC_XSD_URI, f.getAbsoluteFile() ).getValue();
                midiSpecTable.put( p.getName(), p );
                midiSpecArray.add( p );
            }
        }
        catch( Exception e )
        {
            logger.log( Level.SEVERE, "Failed to loading MIDI Specification file.", e );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public void dispose()
    {
        logger.info( "******************* DISPOSE CONTEXT - BEGIN *******************" );

        logger.info( "dispose player" );
        player.dispose();

        logger.info( "dispose midiEventListeners" );
        midiEventHandlers.clear();

        logger.info( "close all midi devices" );
        MIDIDeviceManager.closeAllDevice();

        logger.info( "******************* DISPOSE CONTEXT - END *******************" );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized public void loadDeviceFile() throws JAXBException, SAXException
    {
        loadDeviceFile( config.getDevicefile().getFile() );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public void loadDeviceFile( String fileName ) throws JAXBException, SAXException
    {
        device.load( fileName );
        config.getDevicefile().setFile( fileName );
        logger.info( "loaded device file: "+ fileName );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public void setMidiOut( String devName )
    {
        MidiDevice d = MIDIDeviceManager.searchMidiOutDevice( devName );
        if( d != null )
        {
            player.setMidiOutDevice( d );
            config.getMidiout().setName( devName );
            logger.info( "set MIDI OUT: "+ devName );
        }
        else
        {
            logger.warning( devName + " is invalid name." );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public void play( String file )
    {
        play( new File( file ) );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public boolean play( File f )
    {
        boolean ret;
        MidiDevice midiOut = MIDIDeviceManager.searchMidiOutDevice( config.getMidiout().getName() );
        MidiDevice midiIn  = MIDIDeviceManager.searchMidiInDevice( config.getMidiin().getName() );
        player.setMidiOutDevice( midiOut );
        player.setMidiInDevice( midiIn );

        ret = player.play( f );
        config.getLastdirectory().setDir( f.getParent() );

        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * MIDI IN デバイスからの MIDI 信号を MIDI OUT へ送出するリアルタイム演奏を開始する。
     */
    synchronized public boolean startRealTimeInput()
    {
        MidiDevice midiOut = MIDIDeviceManager.searchMidiOutDevice( config.getMidiout().getName() );
        MidiDevice midiIn  = MIDIDeviceManager.searchMidiInDevice( config.getMidiin().getName() );

        player.setMidiOutDevice( midiOut );
        player.setMidiInDevice( midiIn );

        return player.startRealTimeInput();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized public void stop()
    {
        player.stop();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public boolean isPlaying()
    {
        return player != null && player.isPlaying();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @return the config
     */
    public ApplicationConfig getConfig()
    {
        return config;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @return the device
     */
    public Device getDevice()
    {
        return device;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized  public Vector<File> getDeviceInfoList()
    {
        Vector<File> ret = new Vector<File>( 32 );
        File dir = new File(  AppConstants.DEVICE_DEF_DIR );

        File[] files = dir.listFiles( new FilenameFilter() {
            @Override
            public boolean accept( File dir, String name )
            {
                return name.endsWith( ".xml" );
            }
        });

        for( File f : files )
        {
            ret.addElement( f );
        }

        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @return the midiSpecTable
     */
    public Hashtable<String, Spec> getMidiSpecTable()
    {
        return midiSpecTable;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @return the midiSpecArray
     */
    public ArrayList<Spec> getMidiSpecArray()
    {
        return midiSpecArray;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public int getCurrentTimeInSecond()
    {
        return player.getCurrentTimeInSecond();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public int getPlayTimeInSecond()
    {
        return player.getPlayTimeInSecond();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public int getBPM()
    {
        return player.getBPM();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized public void saveAppConfig()
    {
        JAXBUtil<ApplicationConfig> appConfUtil = new JAXBUtil<ApplicationConfig>( ApplicationConfig.class );
        try
        {
            logger.info( "Saving the config file : " + AppConstants.APP_CONFIG_FILE  );
            appConfUtil.writeToFile( configElement, XSDConstants.APPCONFIG_XSD_URI, new File( AppConstants.APP_CONFIG_FILE) );
            logger.info( "Saving the config file successfully. " );
        }
        catch( Throwable e )
        {
            logger.log( Level.SEVERE, "ERROR", e );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @return the rendererPluginManager
     */
    public PluginManager<RendererPlugin> getRendererPluginManager()
    {
        return rendererPluginManager;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public void addMidiEventHandler( MidiEventHandler e )
    {
        synchronized( midiEventHandlers )
        {
            if( e != null && ! midiEventHandlers.contains( e ) )
            {
                midiEventHandlers.add( e );
                logger.info( "added MIDIEventListener : " + midiEventHandlers.size() );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public void removeMidiEventHandler( MidiEventHandler e )
    {
        synchronized( midiEventHandlers )
        {
            if( e != null && midiEventHandlers.contains( e ) )
            {
                midiEventHandlers.remove( e );
                logger.info( "removed MIDIEventListener : " + midiEventHandlers.size() );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public boolean handleMidiEvent( int ch, int status, byte[] data, int length )
    {
        boolean ret = false;

        synchronized( midiEventHandlers )
        {
            for( MidiEventHandler e : midiEventHandlers )
            {
                boolean b = e.handleMidiEvent( ch, status, data, length );
                if( b && !ret )
                {
                    ret = true;
                }
            }
        }
        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public boolean handleSysExEvent( byte[] data, int length )
    {
        boolean ret = false;

        synchronized( midiEventHandlers )
        {
            for( MidiEventHandler e : midiEventHandlers )
            {
                boolean b = e.handleSysExEvent( data, length );
                if( b && !ret )
                {
                    ret = true;
                }
            }
        }
        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public boolean handleMetaEvent( int type, byte[] data, int length )
    {
        boolean ret = false;

        synchronized( midiEventHandlers )
        {
            for( MidiEventHandler e : midiEventHandlers )
            {
                boolean b = e.handleMetaEvent( type, data, length );
                if( b && !ret )
                {
                    ret = true;
                }
            }
        }
        return ret;
    }
}
