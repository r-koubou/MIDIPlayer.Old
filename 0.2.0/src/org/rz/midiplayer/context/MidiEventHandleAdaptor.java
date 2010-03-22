
package org.rz.midiplayer.context;

/**
 * MidiEventHandler のイベントを受け取るアダプタクラス。
 * すべてのメソッドは空の実装であり、サブクラスは必要なメソッドを適宜オーバーライドし、実装を行う。
 * @author rz
 */
abstract public class MidiEventHandleAdaptor implements MidiEventHandler
{
    @Override
    public void addMidiChannelEventListener( MidiChannelEventListener e )
    {
    }

    @Override
    public boolean handleMetaEvent( int type, byte[] data, int length )
    {
        return false;
    }

    @Override
    public boolean handleMidiEvent( int ch, int status, byte[] data, int length )
    {
        return false;
    }

    @Override
    public boolean handleSysExEvent( byte[] data, int length )
    {
        return false;
    }

    @Override
    public void removeAllMidiChannelEventListeners()
    {
    }

    @Override
    public void removeMidiChannelEventListener( MidiChannelEventListener e )
    {
    }
}
