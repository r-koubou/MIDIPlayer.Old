
package org.rz.midiplayer.midi;

import java.util.Vector;
import java.util.logging.Level;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import org.rz.midiplayer.logging.Loggable;

/**
 * MIDI デバイス関連の管理クラス。
 * @author rz
 */
public class MIDIDeviceManager implements Loggable
{
    ////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private MIDIDeviceManager()
    {}

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * OS にインストールされている MIDI デバイスのリストを取得する。
     */
    synchronized static public Vector<MidiDevice> getDeviceList()
    {
        Vector<MidiDevice> ret = new Vector<MidiDevice>( 32 );

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        if( infos != null )
        {
            for( MidiDevice.Info i : infos )
            {
                try
                {
                    ret.add( MidiSystem.getMidiDevice( i ) );
                }
                catch( MidiUnavailableException e )
                {
                    logger.log( Level.WARNING, "cannot get a MIDI Device.", e );
                }
            }
        }

        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * OS にインストールされている MIDI OUT デバイスのリストを取得する。
     */
    synchronized static public Vector<MidiDevice> getMidiOutDeviceList()
    {
        Vector<MidiDevice> list = getDeviceList();
        Vector<MidiDevice> ret  = new Vector<MidiDevice>( 16 );

        for( MidiDevice d : list )
        {
            if( d.getMaxReceivers() != 0 )
            {
                ret.add( d );
            }
        }
        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * OS にインストールされている MIDI IN デバイスのリストを取得する。
     */
    synchronized static public Vector<MidiDevice> getMidiInDeviceList()
    {
        Vector<MidiDevice> list = getDeviceList();
        Vector<MidiDevice> ret  = new Vector<MidiDevice>( 16 );

        for( MidiDevice d : list )
        {
            if( d.getMaxTransmitters() != 0 )
            {
                ret.add( d );
            }
        }
        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 指定した MIDI OUT デバイスを取得する。
     * @see #existDevice(java.lang.String)
     */
    static public MidiDevice searchMidiOutDevice( String deviceName )
    {
        Vector<MidiDevice> list = getMidiOutDeviceList();
        for( MidiDevice i : list )
        {
            if( i.getDeviceInfo().getName().equals( deviceName ) )
            {
                return i;
            }
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 指定した MIDI OUT デバイスを取得する。
     * @see #existDevice(java.lang.String)
     */
    static public MidiDevice searchMidiInDevice( String deviceName )
    {
        Vector<MidiDevice> list = getMidiInDeviceList();
        for( MidiDevice i : list )
        {
            if( i.getDeviceInfo().getName().equals( deviceName ) )
            {
                return i;
            }
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 指定した MIDI OUT デバイスが取得可能か判定する。
     * @see  #search(java.lang.String)
     */
    static public boolean existMidiOutDevice( String deviceName )
    {
        Vector<MidiDevice> list = getMidiOutDeviceList();
        for( MidiDevice i : list )
        {
            if( i.getDeviceInfo().getName().equals( deviceName ) )
            {
                return true;
            }
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 指定した MIDI IN デバイスが取得可能か判定する。
     * @see  #search(java.lang.String)
     */
    static public boolean existMidiInDevice( String deviceName )
    {
        Vector<MidiDevice> list = getMidiInDeviceList();
        for( MidiDevice i : list )
        {
            if( i.getDeviceInfo().getName().equals( deviceName ) )
            {
                return true;
            }
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    static public void close( MidiDevice m )
    {
        try
        {
            for( Transmitter t : m.getTransmitters() )
            {
                close( t );
            }
        }
        catch( Throwable e ) {}

        try
        {
            for( Receiver r : m.getReceivers() )
            {
                close( r );
            }
        }
        catch( Throwable e )
        {
        }

        try
        {
            if( m.isOpen() )
            {
                m.close();
                logger.info( "Closed MIDI Device: " + m.getDeviceInfo().getName() + "@" + m.hashCode() );
            }
        }
        catch( Throwable e )
        {
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    static public void close( Transmitter t )
    {
        try
        {
            t.close();
            logger.info( "Closed Transmitter: " + t.getClass().getName() + "@" + t.hashCode() );
        }
        catch( Throwable e )
        {
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    static public void close( Receiver r )
    {
        try
        {
            r.close();
            logger.info( "Closed Receiver: " + r.getClass().getName() + "@" + r.hashCode() );
        }
        catch( Throwable e )
        {
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized static public void closeAllDevice()
    {
        Vector<MidiDevice> list = getDeviceList();
        for( MidiDevice i : list )
        {
            if( i.isOpen() )
            {
                close( i );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    static public void stop( Sequencer seq )
    {
        try
        {
            if( seq.isRunning() )
            {
                seq.stop();
                logger.info( "Sequencer is stopped. : " + seq.getDeviceInfo().getName()  );
            }
        }
        catch( Throwable e ){}
    }

}
