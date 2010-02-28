
package org.rz.midiplayer.plugin;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import javax.xml.bind.JAXBException;
import org.rz.midiplayer.AppConstants;
import org.rz.midiplayer.context.Context;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.plugin.info.PluginInfo;
import org.rz.midiplayer.util.PathUtil;
import org.rz.midiplayer.xmlmodule.JAXBUtil;
import org.rz.midiplayer.xmlmodule.XSDConstants;
import org.xml.sax.SAXException;

/**
 *
 * @author rz
 */
public class PluginManager<PLUGIN_BASE extends Plugin> implements Loggable
{
    protected final String PLUGIN_DIR;

    private Hashtable<PluginInfo,String> pluginInfoList = new Hashtable<PluginInfo,String>( 64 );
    private Hashtable<PluginInfo, Class<?>> pluginList = new Hashtable<PluginInfo, Class<?>>( 64 );

    private final Class<PLUGIN_BASE> pluginBaseClass;

    ////////////////////////////////////////////////////////////////////////////
    /**
     * PluginManager インスタンスを生成する。
     */
    public PluginManager( Class<PLUGIN_BASE> c, String pluginDir ) throws IllegalArgumentException, JAXBException, SAXException, IOException, ClassNotFoundException
    {
        PLUGIN_DIR      = pluginDir;
        pluginBaseClass = c;

        File f = new File( pluginDir );
        if( ! f.isDirectory() )
        {
            throw new IllegalArgumentException( "pluginDir is not directory : " + pluginDir );
        }
        if( ! f.exists() )
        {
            throw new IllegalArgumentException( "pluginDir is not exist : " + pluginDir );
        }

        refreshPluginInfo();
        loadClasses();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    private void refreshPluginInfo() throws JAXBException, SAXException, IOException
    {
        logger.info( "Searching plugin info (" + AppConstants.PLUGIN_DEF_FILENAME + ") from " + PLUGIN_DIR );

        File dir = new File( PLUGIN_DIR );

        File[] dirs = dir.listFiles( new FileFilter() {

            @Override
            public boolean accept( File pathname )
            {
                if( pathname.isDirectory() )
                {
                    File pluginXml = new File( PathUtil.buildPath( pathname.toString(), AppConstants.PLUGIN_DEF_FILENAME ) );
                    if( pluginXml.exists() )
                    {
                        logger.info( "Plugin dir  : " + pathname.getName() );
                        return true;
                    }
                    logger.info( AppConstants.PLUGIN_DEF_FILENAME +  " not found. this directory is ignored:" + pathname );
                    return false;
                }
                return false;
            }
        });

        if( dirs == null || dirs.length == 0 )
        {
            logger.warning( "PLUGIN IS EMPTY... : " + PLUGIN_DIR );
            return;
        }

        Hashtable<PluginInfo,String> newList = new Hashtable<PluginInfo,String>( 64 );

        for( File d : dirs )
        {
            String path = PathUtil.buildPath( d.toString(), AppConstants.PLUGIN_DEF_FILENAME );
            logger.info( "Loading plugin info (" + AppConstants.PLUGIN_DEF_FILENAME + ") from " + path );
            File xml = new File( path );
            newList.put( loadPluginInfo( new File( path ) ), d.getCanonicalPath() );
        }

        pluginInfoList = newList;

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    protected PluginInfo loadPluginInfo( File xmlPath ) throws JAXBException, SAXException
    {
        JAXBUtil<PluginInfo> jaxb = new JAXBUtil<PluginInfo>( PluginInfo.class );
        return jaxb.loadFromFile( XSDConstants.PLUGINIFO_XSD_URI, xmlPath ).getValue();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private void loadClasses() throws IOException, ClassNotFoundException
    {
        Enumeration<PluginInfo> en = pluginInfoList.keys();
        pluginList.clear();

        while( en.hasMoreElements() )
        {
            PluginInfo inf = en.nextElement();
            String dir     = pluginInfoList.get( inf );

            Class<?> claszz = loadClass( dir, inf ) ;
            pluginList.put( inf, claszz );
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    @SuppressWarnings( "unchecked" )
    public PLUGIN_BASE newInstance( Context ctx, PluginInfo inf ) throws ClassNotFoundException, InstantiationException, IllegalAccessException
    {
        Class<?> c                       = pluginList.get( inf );
        Class<? extends  PLUGIN_BASE> cc = null;

        if( c == null )
        {
            throw new ClassNotFoundException( "plugin class " + inf.getClazz() + " is not found..." );
        }

        cc = c.asSubclass( pluginBaseClass );

        PLUGIN_BASE ret  = cc.newInstance();
        String pluginDir = pluginInfoList.get( inf );

        if( pluginDir == null )
        {
            throw new IllegalArgumentException( "inf is illegal value( pluginDir is null )" );
        }

        ret.onInit( ctx, pluginDir );

        return ret;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private Class<?> loadClass( String dir, PluginInfo inf ) throws IOException, ClassNotFoundException
    {
        logger.info( "create instance : " + inf.getClazz()  );

        File jarFile = new File(  PathUtil.buildPath( dir, inf.getJarfile()) );
        URL jarURL   = jarFile.getCanonicalFile().toURI().toURL();

        URLClassLoader loader = new URLClassLoader( new URL[] { jarURL } );
        Class<?> clazz = loader.loadClass( inf.getClazz() );

        for( Class<?> i : clazz.getInterfaces() )
        {
            if( i == pluginBaseClass )
            {
                logger.info( "found class <" + clazz.getName() + "> implements " + pluginBaseClass.getName() );
                return clazz;
            }
        }

        Class<?> superClass = clazz.getSuperclass();
        while( superClass != Object.class )
        {
            if( superClass == pluginBaseClass )
            {
                logger.info( "found class <" + clazz.getName() + "> extends " + pluginBaseClass.getName() );
                return clazz;
            }

            for( Class<?> i : superClass.getInterfaces() )
            {
                if( i == pluginBaseClass )
                {
                    logger.info( "found class <" + clazz.getName() + "> implements " + pluginBaseClass.getName() );
                    return clazz;
                }
            }

            logger.info( superClass.getName() );
            superClass = superClass.getSuperclass();
        }

        throw new ClassNotFoundException();

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public boolean isEmpty()
    {
        return pluginInfoList.isEmpty();
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public boolean existPlugin( String name )
    {
        for( PluginInfo i : pluginInfoList.keySet() )
        {
            if( i.getName().equals( name ) )
            {
                logger.info( "exist plugin : " + name  );
                return true;
            }
        }
        return false;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public PluginInfo getPluginInfo( String name )
    {
        for( PluginInfo i : pluginInfoList.keySet() )
        {
            logger.info( "searching for plugin info, name=" + name + ", inf=" + i.getName() );
            if( i.getName().equals( name ) )
            {
                logger.info( "exist plugin info : " + name  );
                return i;
            }
        }
        return null;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized public PluginInfo[] getPluginInfoList()
    {
        PluginInfo[] ret = new PluginInfo[ pluginInfoList.size() ];

        if( ! pluginInfoList.isEmpty() )
        {
            int i = 0;
            Iterator<PluginInfo> it = pluginInfoList.keySet().iterator();
            while( it.hasNext() )
            {
                ret[ i ] = it.next();
                i++;
            }
        }

        return ret;
    }
}
