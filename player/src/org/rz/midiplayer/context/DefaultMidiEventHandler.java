
package org.rz.midiplayer.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.xmlmodule.deviceinfo.InstType;
import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;
import org.rz.midiplayer.xmlmodule.midispec.DrumPartPc;
import org.rz.midiplayer.xmlmodule.midispec.DrumPartSysExec;
import org.rz.midiplayer.xmlmodule.midispec.MeloPartPc;
import org.rz.midiplayer.xmlmodule.midispec.MeloPartSysExec;
import org.rz.midiplayer.xmlmodule.midispec.Spec;

/**
 *
 * @author rz
 */
public class DefaultMidiEventHandler implements Loggable, MidiEventHandler
{
    protected final Context context;
    protected final MidiChannel[] midiChannels = new MidiChannel[ 16 ];
    protected final ArrayList<MidiChannelEventListener> midiChannelEventListeners = new ArrayList<MidiChannelEventListener>( 32 );

    private String currentMidiSpec;

    private int pcMsb;
    private int pcLsb;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * DefaultSimpleHandler インスタンスを生成する。
     */
    public DefaultMidiEventHandler( Context ctx )
    {
        context = ctx;

        for( int i = 0; i < 16; i++ )
        {
            midiChannels[ i ] = new MidiChannel();
        }

        setCurrentMidiSpec( ctx.getDevice().getDeviceInfo().getDefaultMode() );

    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * @return the currentMidiSpec
     */
    public String getCurrentMidiSpec()
    {
        return currentMidiSpec;
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     * @param currentMidiSpec the currentMidiSpec to set
     */
    synchronized public void setCurrentMidiSpec( String s )
    {
        if( s != null )
        {
            currentMidiSpec = s;
            reset();
        }
        else
        {
            logger.warning( "********* s is null" );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public void reset()
    {
        int value;
        Spec spec = context.getMidiSpecTable().get( currentMidiSpec );
        Instrument[] inst = getDefaultInstrumentList();

        for( int i = 0; i < 16; i++ )
        {
            midiChannels[ i ].reset();
            midiChannels[ i ].setInstrument( inst[ i ] );
        }

        value = spec.getDrumpart().getDefault();
        if( value >= 0 )
        {
            midiChannels[ value ].setDrumMode( true );
        }

        pcMsb = 0;
        pcLsb = 0;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public MidiChannel getMidiChannel( int ch )
    {
        return midiChannels[ ch ];
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void addMidiChannelEventListener( MidiChannelEventListener e )
    {
        synchronized( midiChannelEventListeners )
        {
            if( e != null && ! midiChannelEventListeners.contains( e ) )
            {
                midiChannelEventListeners.add( e );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void removeAllMidiChannelEventListeners()
    {
        synchronized( midiChannelEventListeners )
        {
            midiChannelEventListeners.clear();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public void removeMidiChannelEventListener( MidiChannelEventListener e )
    {
        synchronized( midiChannelEventListeners )
        {
            if( e != null )
            {
                midiChannelEventListeners.remove( e );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public Instrument[] getDefaultInstrumentList()
    {
        Instrument[] ret  = new Instrument[ 16 ];
        Spec spec = context.getMidiSpecTable().get( currentMidiSpec );
        InstrumentList instList = context.getDevice().getInstrumentList();

        int defaultMeloPC  = spec.getMelopart().getDefaultpc();
        int defaultMeloMSB = spec.getMelopart().getDefaultmsb();
        int defaultMeloLSB = spec.getMelopart().getDefaultlsb();

        int defaultDrumPC  = spec.getDrumpart().getDefaultpc();
        int defaultDrumMSB = spec.getDrumpart().getDefaultmsb();
        int defaultDrumLSB = spec.getDrumpart().getDefaultlsb();

        int defaultDrumCH  = spec.getDrumpart().getDefault();

        for( int i = 0; i < 16; i++ )
        {
            if( i == defaultDrumCH )
            {
                ret[ i ] = instList.search(
                        currentMidiSpec,
                        InstType.DRUM,
                        defaultDrumPC,
                        defaultDrumMSB,
                        defaultDrumLSB
                );
            }
            else
            {
                ret[ i ] = instList.search(
                        currentMidiSpec,
                        InstType.MELO,
                        defaultMeloPC,
                        defaultMeloMSB,
                        defaultMeloLSB
                );
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
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public final boolean handleMidiEvent( int ch, int status, byte[] data, int length )
    {
        MidiChannel channel = midiChannels[ ch ];

        switch( status & ~0xf )
        {
            //--------------------------------------------------------------------------
            // note off
            //--------------------------------------------------------------------------
            case 0x80:
            {
                int data1 = data[ 1 ];

                channel.getNoteInfo( data1 ).noteOff();
                dispatchNoteOff( ch, data[ 1 ] );
            }
            return true;

            //--------------------------------------------------------------------------
            // note on
            //--------------------------------------------------------------------------
            case 0x90:
            {
                int data1   = data[ 1 ];
                int data2   = data[ 2 ];

                if( data2 == 0 )
                {
                    channel.getNoteInfo( data1 ).noteOff();
                    dispatchNoteOff( ch, data1 );
                }
                else
                {
                    channel.getNoteInfo( data1 ).noteOn( data2 );
                    dispatchNoteOn( ch, data1, data2 );
                }
            }
            return true;

            //--------------------------------------------------------------------------
            // CC
            //--------------------------------------------------------------------------
            case 0xB0:
            {
                int cc  = data[ 1 ];
                int v   = data[ 2 ];

                if( cc == 0 )
                {
                    pcMsb = v;
                }
                else if( cc == 32 )
                {
                    pcLsb = v;
                }

                channel.setCcValue( cc, v );
                dispatchCcValue( ch, cc, v );
            }
            return true;

            //--------------------------------------------------------------------------
            // Program change
            //--------------------------------------------------------------------------
            case 0xC0:
            {
                programChangeImpl( ch, data[ 1 ], pcMsb, pcLsb );
            }
            return true;

            //--------------------------------------------------------------------------
            // Pitch Bend
            //--------------------------------------------------------------------------
            case 0xE0:
            {
                int data1   = data[ 1 ] & 0x7f;
                int data2   = data[ 2 ] & 0x7f;
                int bend    = ( data2 << 7 ) | data1;

                channel.setPitchBend( ch );
                dispatchPitchbendValue( ch, bend );
            }
            return true;
        }

        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override
    public boolean handleSysExEvent( byte[] data, int length )
    {
        // リセット系のエクスクルーシブメッセージ判定
        {
            ArrayList<Spec> sp = context.getMidiSpecArray();
            List<org.rz.midiplayer.xmlmodule.deviceinfo.InstrumentList> instList  = context.getDevice().getDeviceInfo().getInstruments();

            for( Spec s : sp )
            {
                if( Arrays.equals( s.getReset().getSysexec(), data ) )
                {
                    for( org.rz.midiplayer.xmlmodule.deviceinfo.InstrumentList inst : instList )
                    {
                        if( ! inst.getMode().equals( s.getName() ) )
                        {
                            continue;
                        }

                        setCurrentMidiSpec( s.getName() );
                        reset();

                        Instrument[] defaultInst = getDefaultInstrumentList();
                        for( int i = 0; i < 16; i++ )
                        {
                            midiChannels[ i ].setInstrument( defaultInst[ i ] );
                            dispatchProgramChange( i, defaultInst[ i ] );
                        }

                        logger.info( "********** RESET EXCLUSIVE MESSAGE : " + s.getName() );
                        return true;
                    }
                }
            }
        }

        // GS 系のインスト/ドラムパート切り替えエクスクルーシブ
        // １：ドラムパート ON 系
        // 凡例：SysEx: [USE FOR RHYTHM PART] data value = 1 or 2 のメッセージ
        {
            Spec sp = context.getMidiSpecTable().get( getCurrentMidiSpec() );
            List<DrumPartSysExec> dr = sp.getDrumpart().getDrumpartsysexec();
            InstrumentList instList  = context.getDevice().getInstrumentList();

            for( DrumPartSysExec d : dr )
            {
                if( Arrays.equals( d.getSysexec(), data ) )
                {
                    int ch     = d.getChannel();
                    Instrument now     = midiChannels[ ch ].getInstrument();
                    Instrument newInst = instList.search( currentMidiSpec, InstType.DRUM, now.getPc(), now.getMsb(), now.getLsb() );

                    midiChannels[ ch ].setDrumMode( true );
                    midiChannels[ ch ].setInstrument( newInst );

                    dispatchProgramChange( ch, newInst );

                    logger.info( "(Maybe) GS : SysEx [USE FOR RHYTHM PART (ON)] ch=" + ch );
                    return true;
                }
            }
        }
        // ２：ドラムパート OFF 系
        // 凡例：SysEx: [USE FOR RHYTHM PART] data value = 0 のメッセージ
        {
            Spec sp = context.getMidiSpecTable().get( getCurrentMidiSpec() );
            List<MeloPartSysExec> ml = sp.getMelopart().getMelopartsysexec();
            InstrumentList instList  = context.getDevice().getInstrumentList();

            for( MeloPartSysExec m : ml )
            {
                if( Arrays.equals( m.getSysexec(), data ) )
                {
                    int ch     = m.getChannel();
                    Instrument now     = midiChannels[ ch ].getInstrument();
                    Instrument newInst = instList.search( currentMidiSpec, InstType.MELO, now.getPc(), now.getMsb(), now.getLsb() );

                    midiChannels[ ch ].setDrumMode( false );
                    midiChannels[ ch ].setInstrument( newInst );

                    dispatchProgramChange( ch, newInst );

                    logger.info( "(Maybe) GS : SysEx [USE FOR RHYTHM PART (OFF)] ch=" + ch );
                    return true;
                }
            }
        }

        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void programChangeImpl( int ch, int pc, int msb, int lsb )
    {
        Spec spec               = context.getMidiSpecTable().get( currentMidiSpec );
        InstrumentList instList = context.getDevice().getInstrumentList();
        MidiChannel channel     = midiChannels[ ch ];

        //--------------------------------------------------------------------------
        // ドラムマップから検索
        //--------------------------------------------------------------------------
        {
            List<DrumPartPc> dr = spec.getDrumpart().getDrumpartpc();
            if( ! dr.isEmpty() )
            {
                for( DrumPartPc p : dr )
                {
                    int _ch  = p.getCh();
                    int _msb = p.getMsb();
                    int _lsb = p.getLsb();

                    if( _ch  < 0 ) { _ch  = ch; }
                    if( _msb < 0 ) { _msb = msb; }
                    if( _lsb < 0 ) { _lsb = lsb; }

                    if( _ch == ch && _msb == msb && _lsb == lsb )
                    {
                        logger.info( "1: Searching for Drum inst(CC) : ch=" + ch + ", pc=" + pc + ", msb=" + msb + ", lsb=" + lsb );
                        Instrument newInst =  instList.search( currentMidiSpec, InstType.DRUM, pc, msb, lsb );
                        channel.setInstrument( newInst );
                        channel.setDrumMode( true );

                        dispatchProgramChange( ch, newInst );
                        return;
                    }
                }
            }
        }

        //--------------------------------------------------------------------------
        // なければメロディー楽器のマップから検索
        //--------------------------------------------------------------------------
        {
            List<MeloPartPc> ml = spec.getMelopart().getMelopartpc();

            if( ! ml.isEmpty() )
            {
                for( MeloPartPc p : ml )
                {
                    int _ch  = p.getCh();
                    int _msb = p.getMsb();
                    int _lsb = p.getLsb();

                    if( _ch  < 0 ) { _ch  = ch; }
                    if( _msb < 0 ) { _msb = msb; }
                    if( _lsb < 0 ) { _lsb = lsb; }

                    if( _ch == ch && _msb == msb && _lsb == lsb )
                    {
                        logger.info( "2: Searching for Melo inst(CC) : ch=" + ch + ", pc=" + pc + "msb=" + msb + ", lsb=" + lsb );
                        Instrument newInst = instList.search( currentMidiSpec, InstType.MELO, pc, msb, lsb );
                        channel.setInstrument( newInst );
                        channel.setDrumMode( false );

                        dispatchProgramChange( ch, newInst );
                        return;
                    }

                }
            }
        }

        //--------------------------------------------------------------------------
        // Program change でメロディ/ドラムパートの切り替えが発生しないのであれば
        // 現在のモードを維持したまま検索
        //--------------------------------------------------------------------------
        {
            InstType type = channel.isDrumMode() ? InstType.DRUM : InstType.MELO;
            Instrument newInst = instList.search( currentMidiSpec, type, pc, msb, lsb );
            channel.setInstrument( newInst );
            dispatchProgramChange( ch, newInst );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void dispatchProgramChange( int ch, Instrument i )
    {
        synchronized( midiChannelEventListeners )
        {
            for( MidiChannelEventListener e : midiChannelEventListeners )
            {
                e.programChange( ch, i );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void dispatchNoteOn( int ch, int noteNo, int vel )
    {
        synchronized( midiChannelEventListeners )
        {
            for( MidiChannelEventListener e : midiChannelEventListeners )
            {
                e.noteOn( ch, noteNo, vel );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void dispatchNoteOff( int ch, int noteNo )
    {
        synchronized( midiChannelEventListeners )
        {
            for( MidiChannelEventListener e : midiChannelEventListeners )
            {
                e.noteOff( ch, noteNo );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void dispatchCcValue( int ch, int ccNo, int value )
    {
        synchronized( midiChannelEventListeners )
        {
            for( MidiChannelEventListener e : midiChannelEventListeners )
            {
                e.changedCcValue( ch, ccNo, value );
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void dispatchPitchbendValue( int ch, int value )
    {
        synchronized( midiChannelEventListeners )
        {
            for( MidiChannelEventListener e : midiChannelEventListeners )
            {
                e.changedPitchbend( ch, value );
            }
        }
    }

}
