
package org.rz.midiplayer.context;

import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;

/**
 *
 * @author rz
 */
public interface MidiChannelEventListener
{
    public void changedCcValue( int ch, int ccNo, int value );
    public void changedPitchbend( int ch, int value );
    public void noteOff( int ch, int note );
    public void noteOn( int ch, int note, int vel );
    public void programChange( int ch, Instrument inst );

    MidiChannelEventListener nullEventListener = new MidiChannelEventListener() {

        @Override
        public void changedCcValue( int ch, int ccNo, int value ) {}

        @Override
        public void changedPitchbend( int ch, int value ){}

        @Override
        public void noteOff( int ch, int note ){}

        @Override
        public void noteOn( int ch, int note, int vel ){}

        @Override
        public void programChange( int ch, Instrument inst ){}
    };

}
