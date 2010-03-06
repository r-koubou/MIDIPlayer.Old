
package org.rz.midiplayerplugin.renderer;

/**
 *
 * @author rz
 */
public interface NoteEventListener
{
    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    void noteOn( int ch, int noteNo, int vel );

    ////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    void noteOff( int ch, int noteNo );
}
