
package org.rz.midiplayer.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.midi.MidiEventListener;
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
public class DefaultSimpleHandler implements Loggable, SimpleHandler, MidiEventListener
{
    protected final Context context;

    private String currentMidiSpec;
    private final int[] drumChennels  = new int[ 16 ];
    private int maxAssignDrumChannels;

    private int ccMsb;
    private int ccLsb;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * DefaultSimpleHandler インスタンスを生成する。
     */
    public DefaultSimpleHandler( Context ctx )
    {
        context = ctx;
        setCurrentMidiSpec( ctx.getDevice().getDeviceInfo().getDefaultMode() );

        reset();
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
        Arrays.fill( drumChennels, -1 );

        Spec spec = context.getMidiSpecTable().get( currentMidiSpec );
        
        maxAssignDrumChannels = spec.getDrumpart().getMaxassign();

        value = spec.getDrumpart().getDefault();
        if( value >= 0 )
        {
            drumChennels[ 0 ] = value;
        }

        ccMsb = 0;
        ccLsb = 0;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized public Instrument[] getDefaultInstrumentList()
    {
        Instrument[] ret  = new Instrument[ 16 ];
        int defaultDrumCH = 10;
        Spec spec = context.getMidiSpecTable().get( currentMidiSpec );
        InstrumentList instList = context.getDevice().getInstrumentList();

        if( spec != null )
        {
            defaultDrumCH = spec.getDrumpart().getDefault();
        }

        for( int i = 0; i < 16; i++ )
        {
            if( i == defaultDrumCH )
            {
                ret[ i ] = instList.search( currentMidiSpec, InstType.DRUM, 0, 0, 0 );
            }
            else
            {
                ret[ i ] = instList.search( currentMidiSpec, InstType.MELO, 0, 0, 0 );
            }
        }

        return ret;

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public boolean handleMetaEvent( int type, byte[] data, int length )
    {
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public final boolean handleMidiEvent( int ch, int status, byte[] data, int length )
    {
        switch( status & ~0xf )
        {
            //--------------------------------------------------------------------------
            // note off
            //--------------------------------------------------------------------------
            case 0x80:
            {
                noteOff( ch, data[ 1 ] );
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
                    noteOff( ch, data1 );
                }
                else
                {
                    noteOn( ch, data1, data2 );
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
                    ccMsb = v;
                }
                else if( cc == 32 )
                {
                    ccLsb = v;
                }

                changedCcValue( ch, cc, v );
            }
            return true;

            //--------------------------------------------------------------------------
            // Program change
            //--------------------------------------------------------------------------
            case 0xC0:
            {
                programChange( ch, getInstrumentOnPc( ch, data[ 1 ], ccMsb, ccLsb ) );
            }
            return true;

            //--------------------------------------------------------------------------
            // Pitch Bend
            //--------------------------------------------------------------------------
            case 0xE0:
            {
                int data1   = data[ 1 ] & 0x7f;
                int data2   = data[ 2 ] & 0x7f;
                changedPitchbend( ch, ( data2 << 7 ) | data1 );
            }
            return true;
        }

        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public boolean handleSysExEvent( byte[] data, int length )
    {
        // リセット系のエクスクルーシブメッセージ判定
        {
            ArrayList<Spec> sp = context.getMidiSpecArray();

            for( Spec s : sp )
            {
                if( Arrays.equals( s.getReset().getSysexec(), data ) )
                {
                    setCurrentMidiSpec( s.getName() );
                    reset();

                    Instrument[] defaultInst = getDefaultInstrumentList();
                    for( int i = 0; i < 16; i++ )
                    {
                        programChange( i, defaultInst[ i ] );
                    }

                    logger.info( "********** RESET EXCLUSIVE MESSAGE : " + s.getName() );
                    return true;
                }
            }
        }

        // GS 系のインスト/ドラムパート切り替えエクスクルーシブ
        // １：ドラムパート ON 系
        // SysEx: [USE FOR RHYTHM PART] data value = 1 or 2 のメッセージ
        {
            Spec sp = context.getMidiSpecTable().get( getCurrentMidiSpec() );
            List<DrumPartSysExec> dr = sp.getDrumpart().getDrumpartsysexec();
            for( DrumPartSysExec d : dr )
            {
                if( Arrays.equals( d.getSysexec(), data ) )
                {
                    int ch     = d.getChannel();
                    int target = d.getTarget();
                    drumChennels[ target ] = ch;
                    logger.info( "GS : SysEx [USE FOR RHYTHM PART (ON)] ch=" + ch + " DT=" + target );
                }
            }
        }
        // ２：ドラムパート OFF 系
        // SysEx: [USE FOR RHYTHM PART] data value = 0 のメッセージ
        {
            Spec sp = context.getMidiSpecTable().get( getCurrentMidiSpec() );
            List<MeloPartSysExec> ml = sp.getMelopart().getMelopartsysexec();
            for( MeloPartSysExec m : ml )
            {
                if( Arrays.equals( m.getSysexec(), data ) )
                {
                    int ch     = m.getChannel();

                    for( int i = 0; i < maxAssignDrumChannels; i++ )
                    {
                        if( drumChennels[ i ] == ch )
                        {
                            drumChennels[ i ] = -1;
                            logger.info( "GS : SysEx [USE FOR RHYTHM PART (OFF)] ch=" + ch );
                            break;
                        }
                    }
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
    private Instrument getInstrumentOnPc( int ch, int pc, int msb, int lsb )
    {
        Spec spec               = context.getMidiSpecTable().get( currentMidiSpec );
        InstrumentList instList = context.getDevice().getInstrumentList();

        //--------------------------------------------------------------------------
        // ドラムマップから検索
        //--------------------------------------------------------------------------
        SEARCH_DRUMMAP:
        {
            List<DrumPartPc> dr = spec.getDrumpart().getDrumpartpc();
            if( ! dr.isEmpty() )
            {
                for( DrumPartPc p : dr )
                {
                    boolean b = false;

                    int _ch  = p.getCh();
                    int _msb = p.getMsb();
                    int _lsb = p.getLsb();

                    if( maxAssignDrumChannels > 0 )
                    {
                        for( int i = 0; i < maxAssignDrumChannels; i++ )
                        {
                            if( drumChennels[ i ] == ch )
                            {
                                b = true;
                                break;
                            }
                        }
                    }
                    else
                    {
                        b = true;
                    }

                    if( ! b )
                    {
                        break SEARCH_DRUMMAP;
                    }

                    if( _ch  < 0 ) { _ch  = ch; }
                    if( _msb < 0 ) { _msb = msb; }
                    if( _lsb < 0 ) { _lsb = lsb; }

                    if( _ch == ch && _msb == msb && _lsb == lsb )
                    {
                        logger.info( "1: Searching for Drum inst(CC) : ch=" + ch + ", pc=" + pc + ", msb=" + msb + ", lsb=" + lsb );
                        return instList.search( currentMidiSpec, InstType.DRUM, pc, msb, lsb );
                    }

                }
            }
            else
            {
                if( maxAssignDrumChannels > 0 )
                {                    
                    for( int i = 0; i < maxAssignDrumChannels; i++ )
                    {
                        if( drumChennels[ i ] == ch )
                        {
                            logger.info( "2: Searching for Drum inst(CC) : ch=" + ch + ", pc=" + pc + "msb=" + msb + ", lsb=" + lsb );
                            return instList.search( currentMidiSpec, InstType.DRUM, pc, msb, lsb );
                        }
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
                    boolean valid = false;

                    int _ch  = p.getCh();
                    int _msb = p.getMsb();
                    int _lsb = p.getLsb();

                    if( _ch  < 0 ) { _ch  = ch; }
                    if( _msb < 0 ) { _msb = msb; }
                    if( _lsb < 0 ) { _lsb = lsb; }

                    if( _ch == ch && _msb == msb && _lsb == lsb )
                    {
                        logger.info( "3: Searching for Melo inst(CC) : ch=" + ch + ", pc=" + pc + "msb=" + msb + ", lsb=" + lsb );
                        return instList.search( currentMidiSpec, InstType.MELO, pc, msb, lsb );
                    }

                }
            }
            // 定義がないなら無条件で検索
            else
            {
                logger.info( "4: Searching for Melo inst(CC) : ch=" + ch + ", pc=" + pc + "msb=" + msb + ", lsb=" + lsb );
                return instList.search( currentMidiSpec, InstType.MELO, pc, msb, lsb );
            }
        }

        return InstrumentList.nullInstrument;
    }

    @Override public void changedCcValue( int ch, int ccNo, int value ) {}
    @Override public void changedMidiSpec( String name ) {}
    @Override public void changedPitchbend( int ch, int value ) {}
    @Override public void noteOff( int ch, int note ) {}
    @Override public void noteOn( int ch, int note, int vel ) {}
    @Override public void programChange( int ch, Instrument inst ) {}

}
