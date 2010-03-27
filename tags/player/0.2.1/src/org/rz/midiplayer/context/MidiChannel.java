
package org.rz.midiplayer.context;

import java.util.Arrays;
import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;

/**
 *
 * @author rz
 */
public class MidiChannel
{

    static public final int SIZEOF_NOTE = 128;
    static public final int SIZEOF_CC   = 128;

    /** 各コントロールチェンジの値を格納する */
    private final int[] cc   = new int[ SIZEOF_CC ];

    /** 各ノートの値（ベロシティ）を格納する */
    private final NoteInfo[] noteInfo = new NoteInfo[ SIZEOF_NOTE ];

    /** ピッチベンド */
    private int pitchBend;

    /** 音色情報 */
    public Instrument instrument = InstrumentList.nullInstrument;

    /***/
    private boolean drumMode;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * MidiChannel インスタンスを生成する。
     */
    public MidiChannel()
    {
        for( int i = 0; i < SIZEOF_NOTE; i++ )
        {
            noteInfo[ i ] = new NoteInfo();
        }
        reset();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public void reset()
    {
        Arrays.fill( cc, 0 );

        for( NoteInfo i : noteInfo )
        {
            i.reset();
        }

        cc[ 7 ]  = 100;  // volume
        cc[ 11 ] = 127;  // expression
        cc[ 10 ] = 63;   // pan
        cc[ 91 ] = 40;   // reverb

        drumMode = false;

        pitchBend = 8192;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public int getCcValue( int ccNo )
    {
        return cc[ ccNo ];
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public void setCcValue( int ccNo, int newValue )
    {
        cc[ ccNo ] = newValue & 0x7f;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public NoteInfo getNoteInfo( int noteNo )
    {
        return noteInfo[ noteNo ];
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public Instrument getInstrument()
    {
        return instrument;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public void setInstrument( Instrument i )
    {
        if( i == null )
        {
            i = InstrumentList.nullInstrument;
        }

        instrument = i;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public int getPitchBend()
    {
        return pitchBend;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public void setPitchBend( int p )
    {
        pitchBend = p & 0x3fff;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public boolean isDrumMode()
    {
        return drumMode;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @param drumMode the drumMode to set
     */
    public void setDrumMode( boolean drumMode )
    {
        this.drumMode = drumMode;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public class NoteInfo
    {
        private boolean noteOn;
        private int velocity;

        ////////////////////////////////////////////////////////////////////////////////
        /**
         *
         */
        public NoteInfo()
        {
            reset();
        }

        ////////////////////////////////////////////////////////////////////////////////
        /**
         *
         */
        public void reset()
        {
            noteOn   = false;
            velocity = 0;
        }

        ////////////////////////////////////////////////////////////////////////////////
        /**
         *
         */
        public boolean isNoteOn()
        {
            return noteOn;
        }

        ////////////////////////////////////////////////////////////////////////////////
        /**
         *
         */
        public void noteOn( int vel )
        {
            noteOn   = true;
            velocity = vel;
        }

        ////////////////////////////////////////////////////////////////////////////////
        /**
         *
         */
        public void noteOff()
        {
            noteOn   = false;
            velocity = 0;
        }

        ////////////////////////////////////////////////////////////////////////////////
        /**
         *
         */
        public int getVelocity()
        {
            return velocity;
        }

        ////////////////////////////////////////////////////////////////////////////////
        /**
         *
         */
        public void setVelocity( int velocity )
        {
            this.velocity = velocity;
        }
    }
}
