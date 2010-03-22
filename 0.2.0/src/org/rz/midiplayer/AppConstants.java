
package org.rz.midiplayer;

import java.io.File;
import org.rz.midiplayer.util.PathUtil;

/**
 *
 * @author rz
 */
public interface AppConstants
{
    String SEPARATOR            = File.separator;
    String APPDIR               = System.getProperty( "user.dir", "." );
    String DEFAULT_APPCONFIG_RES= "/org/rz/midiplayer/xmlmodule/ApplicationConfig.xml.default";

    String DATADIR              = PathUtil.buildPath( APPDIR, "data" );
    String APP_CONFIG_FILE      = PathUtil.buildPath( DATADIR, "ApplicationConfig.xml" );
    String DEVICE_DEF_DIR       = PathUtil.buildPath( DATADIR, "device" );
    String MIDI_DEF_DIR         = PathUtil.buildPath( DATADIR, "midispec" );

    String PLUGIN_BASEDIR       = PathUtil.buildPath( DATADIR, "plugin" );
    String PLUGIN_RENDERER_DIR  = PathUtil.buildPath( PLUGIN_BASEDIR, "renderer" );
    String PLUGIN_DEF_FILENAME  = "plugin.xml";

}
