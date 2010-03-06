
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
    /** �e�R���g���[���`�F���W�̒l���i�[���� */
    public int[] cc   = new int[ 128 ];

    /** �e�m�[�g�̒l�i�x���V�e�B�j���i�[���� */
    public int[] note = new int[ 128 ];

    /** �s�b�`�x���h */
    public int pitchBend;

    /** ���F��� */
    public Instrument instrument = InstrumentList.nullInstrument;

    /***/
    public int level;
    
    ////////////////////////////////////////////////////////////////////////////
    /**
     * MIDIChannel �C���X�^���X�𐶐�����B
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
