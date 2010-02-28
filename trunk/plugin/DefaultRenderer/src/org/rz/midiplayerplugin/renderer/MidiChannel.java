
package org.rz.midiplayerplugin.renderer;

import java.util.Arrays;
import org.rz.midiplayer.context.InstrumentList;
import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;

/**
 *
 * @author rz
 */
public class MidiChannel
{
    /** 各コントロールチェンジの値を格納する */
    public int[] cc   = new int[ 128 ];

    /** 各ノートの値（ベロシティ）を格納する */
    public int[] note = new int[ 128 ];

    /** ピッチベンド */
    public int pitchBend;

    /** 音色情報 */
    public Instrument instrument = InstrumentList.nullInstrument;

    /***/
    public int level;
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * MIDIChannel インスタンスを生成する。
     */
    public MidiChannel()
    {
        reset();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public void reset()
    {
        Arrays.fill( cc, 0 );
        Arrays.fill( note, 0 );

        cc[ 7 ]  = 100;  // volume
        cc[ 11 ] = 127;  // expression
        cc[ 10 ] = 63;   // pan
        cc[ 91 ] = 40;   // reverb

        pitchBend = 8192;
        level     = 0;
    }

}
