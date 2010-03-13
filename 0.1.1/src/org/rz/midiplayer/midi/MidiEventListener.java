
package org.rz.midiplayer.midi;

/**
 * MIDI デバイスに送信される MIDI イベントと同内容のイベントの受信するためのインタフェース。
 * 画面レンダリング時の各種パラメータ情報を保持するような用途で使用する。
 * 
 * @author rz
 */
public interface MidiEventListener
{
   
    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    boolean handleMidiEvent( int ch, int status, byte[] data, int length );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    boolean handleSysExEvent( byte[] data, int length );


    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    boolean handleMetaEvent( int type, byte[] data, int length );
}
