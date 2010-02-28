
package org.rz.midiplayer.logging;


import java.io.File;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.MidiDevice;
import org.rz.midiplayer.midi.MIDIDeviceManager;

/**
 *
 * @author rz
 */
public class Log
{
    static public final Logger logger;
    static public final boolean loggingEnabled;

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * static initializer.
     */
    static
    {
        File enabled = new File( "data/.logging-enabled" );
        loggingEnabled = enabled.exists();

        logger = Logger.getLogger( "MIDIPlayer" );
        try
        {
            Handler consoleHandler    = new ConsoleHandler();
            LogFormatter formatter    = new LogFormatter();

            {
                Logger p = logger.getParent();
                if( p != null )
                {
                    for( Handler h : p.getHandlers() )
                    {
                        p.removeHandler( h );
                    }
                }
            }

            if( loggingEnabled )
            {
                Handler fileHandler = new FileHandler( "log.txt", false );
                logger.addHandler( consoleHandler );
                logger.addHandler( fileHandler );
                logger.setLevel( Level.ALL );
            }
            else
            {
                logger.setLevel( Level.OFF );
            }

            for( Handler h : logger.getHandlers() )
            {
                h.setFormatter( formatter );
            }

            loggingUserEnvironment();

        }
        catch( Throwable e )
        {
            e.printStackTrace();
            System.exit( 1 );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    static private void loggingUserEnvironment()
    {

        try
        {
            Properties prop = System.getProperties();
            Set<Object> set = prop.keySet();
            TreeSet<Object> treeSet = new TreeSet<Object>( set );

            logger.info( "################################## User Environment ##################################" );

            for( Object o : treeSet )
            {
                String key = o.toString();

                if( key.equals( "user.home" ) ||
                    key.equals( "user.name" ) ||
                    key.equals( "user.dir" ) )
                {
                    // 個人情報になり得る不要な情報は残したくないので
                    continue;
                }

                logger.info( o.toString() + ":" + prop.getProperty( o.toString() ) );
            }

            logger.info( "##################################  MIDI Devices  ###################################" );

            logger.info( "*********** ALL ***********" );
            loggingMidiDevice( MIDIDeviceManager.getDeviceList() );

            logger.info( "*********** MIDI OUT ***********" );
            loggingMidiDevice( MIDIDeviceManager.getMidiOutDeviceList() );

            logger.info( "*********** MIDI IN  ***********" );
            loggingMidiDevice( MIDIDeviceManager.getMidiInDeviceList() );

            logger.info(  "#####################################################################################" );
        }
        catch( Throwable e )
        {
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    static private void loggingMidiDevice( Vector<MidiDevice> list )
    {
        for( MidiDevice dev : list )
        {
            MidiDevice.Info inf = dev.getDeviceInfo();
            logger.info( "----------------------------------");
            logger.info( "[Name]"        + inf.getName() );
            logger.info( "[Version]"     + inf.getVersion() );
            logger.info( "[Vendor]"      + inf.getVendor() );
            logger.info( "[Description]" + inf.getDescription() );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private Log(){}

}
