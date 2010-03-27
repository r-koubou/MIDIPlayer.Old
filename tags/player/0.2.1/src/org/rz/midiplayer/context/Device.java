
package org.rz.midiplayer.context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.xml.bind.JAXBException;
import org.rz.midiplayer.logging.Loggable;
import org.rz.midiplayer.xmlmodule.JAXBUtil;
import org.rz.midiplayer.xmlmodule.XSDConstants;
import org.rz.midiplayer.xmlmodule.deviceinfo.DeviceInfo;
import org.rz.midiplayer.xmlmodule.deviceinfo.ImportInfo;
import org.rz.midiplayer.xmlmodule.deviceinfo.InstrumentMap;
import org.xml.sax.SAXException;

/**
 *
 * @author rz
 */
public class Device implements Loggable
{
    private final JAXBUtil<DeviceInfo> jaxbUtil = new JAXBUtil<DeviceInfo>( DeviceInfo.class );

    private final String baseDir;
    private  DeviceInfo deviceInfo;
    private final InstrumentList instrumentList = new InstrumentList();

    ////////////////////////////////////////////////////////////////////////////
    /**
     * Device インスタンスを生成する。
     */
    public Device( String baseDir_ )
    {
        baseDir = baseDir_;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    synchronized void dispose()
    {
        instrumentList.clear();
        deviceInfo = null;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @return the instrumentList
     */
    public InstrumentList getInstrumentList()
    {
        return instrumentList;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * @return the deviceInfo
     */
    public DeviceInfo getDeviceInfo()
    {
        return deviceInfo;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    synchronized void load( String deviceFile ) throws JAXBException, SAXException
    {
        deviceInfo = loadImpl( baseDir + File.separator + deviceFile, null );

        //--------------------------------------------------------------------------
        // 音色データのマッピング
        //--------------------------------------------------------------------------
        {
            instrumentList.clear();

            for( org.rz.midiplayer.xmlmodule.deviceinfo.InstrumentList instList : deviceInfo.getInstruments() )
            {
                for( InstrumentMap i : instList.getMap() )
                {
                    instrumentList.add( instList.getMode(), i );
                }

                if( logger.getLevel() != Level.OFF )
                {
                    int allNum = 0;
                    for( InstrumentMap m : instList.getMap() )
                    {
                        allNum += m.getInstrument().size();
                    }
                    logger.info( "mode:" + instList.getMode() + ", map=" + instList.getMap().size() + ", size=" + allNum );
                }
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private DeviceInfo loadImpl( String deviceFile, DeviceInfo target ) throws JAXBException, SAXException
    {
        if( target == null )
        {
            logger.info( "loading : " + deviceFile );
            target = jaxbUtil.loadFromFile( XSDConstants.DEVICEINFO_XSD_URI, deviceFile ).getValue();
        }

        ArrayList<DeviceInfo> extendList = new ArrayList<DeviceInfo>( 16 );
        List<ImportInfo> impInfoList = target.getImport();

        for( ImportInfo i : impInfoList )
        {
            DeviceInfo inf = loadImpl( baseDir + File.separator + i.getFile(), null );
            extendList.add( inf );
        }

        if( extendList.isEmpty() )
        {
            return target;
        }

        return extend( extendList, target );

    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    private DeviceInfo extend( ArrayList<DeviceInfo> from, DeviceInfo target )
    {
        for( DeviceInfo inf : from )
        {
            for( org.rz.midiplayer.xmlmodule.deviceinfo.InstrumentList list : inf.getInstruments() )
            {
                target.getInstruments().add( list );
            }
        }

        return target;
    }
}
