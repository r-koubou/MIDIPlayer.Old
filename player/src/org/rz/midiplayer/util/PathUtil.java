
package org.rz.midiplayer.util;

import java.io.File;

import static org.rz.midiplayer.AppConstants.SEPARATOR;

/**
 *
 * @author rz
 */
public class PathUtil
{
    private PathUtil() {}

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    static public String buildPath( String baseDir, String path )
    {
        String ret = baseDir + SEPARATOR + path;
        ret = ret.replaceAll( "/",    "\\" + SEPARATOR );
        ret = ret.replaceAll( "\\\\", "\\" + SEPARATOR );
        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    static public String removeSuffix( String path )
    {
        File f = new File( path );
        String p = f.toString();

        p = p.replaceAll( "\\.[^\\.]+$", "" );
        
        return p;
        
    }
}
