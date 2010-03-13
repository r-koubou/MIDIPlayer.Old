
package org.rz.midiplayer.plugin;

import java.io.File;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.logging.Loggable;

/**
 *
 * @author rz
 */
public interface Plugin extends Loggable
{

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    void onInit( Context ctx, String pluginDir );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    void onDispose( Context ctx );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    void onMidiPlayingBefore( File midiFile );

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    void onMidiStoped();
}
