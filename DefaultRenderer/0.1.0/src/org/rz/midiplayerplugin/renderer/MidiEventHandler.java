
package org.rz.midiplayerplugin.renderer;

import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.context.DefaultSimpleHandler;
import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;

/**
 *
 * @author rz
 */
class MidiEventHandler extends DefaultSimpleHandler
{
    private final MidiChannel[] midiChannels;
    private final NoteEventListener noteEventListener;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * MidiEventHandler インスタンスを生成する。
     */
    public MidiEventHandler( Context ctx, MidiChannel[] midiChannels_, NoteEventListener noteEventListener_ )
    {
        super( ctx );
        midiChannels      = midiChannels_;
        noteEventListener = noteEventListener_;

        Instrument[] defaultInst = getDefaultInstrumentList();

        for( int i = 0; i < 16; i++ )
        {
            midiChannels[ i ].instrument = defaultInst[ i ];
        }

    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override public void changedCcValue( int ch, int ccNo, int value )
    {
        midiChannels[ ch ].cc[ ccNo ] = value;
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override public void changedPitchbend( int ch, int value )
    {
        midiChannels[ ch ].pitchBend = value;
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override public void noteOn( int ch, int note, int vel )
    {
        midiChannels[ ch ].level = Math.min( midiChannels[ ch ].level + vel, 127 );
        noteEventListener.noteOn( ch, note, vel );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override public void noteOff( int ch, int note )
    {
        noteEventListener.noteOff( ch, note );
    }

    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @Override public void programChange( int ch, Instrument inst )
    {
        midiChannels[ ch ].instrument = inst;
    }
}
