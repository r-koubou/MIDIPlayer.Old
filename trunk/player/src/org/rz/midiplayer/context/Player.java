
package org.rz.midiplayer.context;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.midi.MIDIDeviceManager;
import org.rz.midiplayer.midi.MidiEventListener;
import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;

/**
 *
 * @author rz
 */
public class Player implements Loggable
{
    /**
     * 再生に使用する MIDI OUT デバイス
     */
    protected MidiDevice midiOutDevice;

    /**
     * 再生時間を格納する。
     */
    protected long playTimeInMicroSeconds;

    /**
     * シーケンサ
     */
    protected Sequencer sequencer;

    /**
     * ロードされた MIDI ファイルのパス
     */
    protected File currentFile;

    /**
     * ロードされた MIDI ファイルのディレクトリ
     */
    protected File currentDirectory;

    /**
     * 代替用途の空実装。
     */
    static private final MidiEventListener emptyMidiEvtListener = new MidiEventListener() {
        @Override public boolean handleMidiEvent( int ch, int status, byte[] data, int length ) { return false; }
        @Override public boolean handleSysExEvent( byte[] data, int length ) { return false; }
        @Override public boolean handleMetaEvent( int type, byte[] data, int length ) { return false; }
    };

    /**
     * レンダラーなどで各チャンネルのパラメータを保持するためにMIDIメッセージの通知を行う宛先。
     */
    protected MidiEventListener midiEventListener = emptyMidiEvtListener;

    /**
     * midiEventListener に通知する MIDI イベントのハンドリング。
     */
    protected final Receiver receiverForListener = new Receiver() {

        @Override
        public void send( MidiMessage message, long time )
        {
            if( midiEventListener == null )
            {
                return;
            }

            byte[] data = message.getMessage();
            int length  = data.length;
            int status  = message.getStatus();
            int channel = status & 0x0f;

            if( status == 0xF0 )
            {
                midiEventListener.handleSysExEvent( data, length );
            }
            else
            {
                midiEventListener.handleMidiEvent( channel, status, data, length );
            }
        }

        @Override
        public void close()
        {
        }
    };

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Player インスタンスを生成する。
     */
    public Player( MidiEventListener lisner )
    {
        midiEventListener = lisner;
        if( lisner == null )
        {
            midiEventListener = emptyMidiEvtListener;
        }

        try
        {
            sequencer = MidiSystem.getSequencer( false );
        }
        catch( Throwable e )
        {
            logger.log( Level.SEVERE, "cannot get the Sequencer INSTANCE.", e );
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Player インスタンスを生成する。
     */
    public Player()
    {
        this( null );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * このプレーヤーが持つ Java/ネイティブリソースの開放を行う。
     */
    public void dispose()
    {
        logger.info( "************** Dispose THIS Player - begin ************** " );

        stop();
        MIDIDeviceManager.close( midiOutDevice );
        MIDIDeviceManager.close( sequencer );

        logger.info( "************** Dispose THIS Player - end ************** " );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 指定された MIDI ファイルを使用し、再生を開始する。
     */
    synchronized public boolean play( File f )
    {
        stop();

        try
        {
            if( f == null )
            {
                logger.warning( "f is null." );
                return false;
            }

            if( sequencer == null )
            {
                logger.warning( "sequencer is null." );
                return false;
            }

            if( midiOutDevice == null )
            {
                logger.warning( "midiOutDevice is null." );
                return false;
            }

            if( ! midiOutDevice.isOpen() )
            {
                midiOutDevice.open();
            }

            sequencer.open();
            sequencer.getTransmitter().setReceiver( midiOutDevice.getReceiver() );
            sequencer.getTransmitter().setReceiver( receiverForListener );

            sequencer.setSequence( MidiSystem.getSequence( f ) );
            playTimeInMicroSeconds = sequencer.getMicrosecondLength();

            currentFile      = f;
            currentDirectory = f.getParentFile();

            logger.info( "Loaded MIDI file : " + f.toString() );

            sequencer.start();
            return true;

        }
        catch( MidiUnavailableException ex )
        {
            logger.log( Level.SEVERE, "cannot open the Sequencer.", ex );
        }
        catch( InvalidMidiDataException im )
        {
            logger.log( Level.SEVERE, "Invalid MIDI data : " + f, im );
        }
        catch( IOException ioe )
        {
            logger.log( Level.SEVERE, "I/O Error : " + f, ioe );
        }

        return false;

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 再生を終了する。
     */
    synchronized  public void stop()
    {
        MIDIDeviceManager.stop( sequencer );
        MIDIDeviceManager.close( midiOutDevice );
        MIDIDeviceManager.close( sequencer );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public boolean isPlaying()
    {
        return sequencer != null && sequencer.isRunning();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public void setMidiOutDevice( MidiDevice dev )
    {
        stop();
        midiOutDevice = dev;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public void setMidiEventLister( MidiEventListener p )
    {
        if( p == null )
        {
            midiEventListener = emptyMidiEvtListener;
        }
        else
        {
            midiEventListener = p;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 現在のファイルの再生時間を取得する。
     */
    public int getPlayTimeInSecond()
    {
        return (int)(playTimeInMicroSeconds / 1000000L );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 現在の再生位置を取得する。
     */
    public int getCurrentTimeInSecond()
    {
        int t = 0;
        try
        {
            t = (int)( sequencer.getMicrosecondPosition() / 1000000L );
        }
        catch( Throwable e )
        {
        }
        return t;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 再生中のMIDIデータの BPM を取得する。
     */
    public int getBPM()
    {
        int ret = 120;

        try
        {
            ret = (int)sequencer.getTempoInBPM();
        }
        catch( Throwable e )
        {
        }

        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * Test code.
     */
    static public void main( String[] args )
    {
        Vector<MidiDevice> devList = MIDIDeviceManager.getDeviceList();
        try
        {
            File file = new File( "C:/Users/Rose/Home/Documents/develop/java/j2se/MIDIPlayer/work/test0.mid" );
            //File file = new File( "C:/Users/Rose/Home/Documents/develop/java/j2se/MIDIPlayer2/temp/gm2.mid" );

            Context ctx = new Context();

            DefaultSimpleHandler h = new DefaultSimpleHandler( ctx ){
                @Override
                public void programChange( int ch, Instrument inst )
                {
                    logger.info( "PC: ch=" + ch + " inst:" + inst.getName() );
                }
            };

            ctx.addMidiEventListener( h );
            ctx.play( file );

            Runtime.getRuntime().addShutdownHook( new Thread(){

                @Override
                public void run()
                {
                    try
                    {
                        MIDIDeviceManager.closeAllDevice();
                    }
                    catch( Throwable e ) {}
                }
                
            });

            Thread.sleep( 20 * 1000 );

            ctx.dispose();

            System.exit( 0 );
        }
        catch( Throwable e )
        {
            e.printStackTrace();
        }
    }
}
