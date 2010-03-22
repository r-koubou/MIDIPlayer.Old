
package org.rz.midiplayer.context;

import org.rz.midiplayer.xmlmodule.deviceinfo.Instrument;

/**
 * MidiChannelEventListener のイベントを受け取るアダプタクラス。
 * すべてのメソッドは空の実装であり、サブクラスは必要なメソッドを適宜オーバーライドし、実装を行う。
 * @author rz
 */
abstract public class MidiChannelEventAdaptor implements MidiChannelEventListener
{
    @Override
    public void changedCcValue( int ch, int ccNo, int value )
    {
    }

    @Override
    public void changedPitchbend( int ch, int value )
    {
    }

    @Override
    public void noteOff( int ch, int note )
    {
    }

    @Override
    public void noteOn( int ch, int note, int vel )
    {
    }

    @Override
    public void programChange( int ch, Instrument inst )
    {
    }
}
