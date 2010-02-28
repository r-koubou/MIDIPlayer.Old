
package org.rz.midiplayer.xmlmodule;

/**
 *
 * @author rz
 */
public class XSDConstants
{
    static public final String MIDISPEC_XSD_URI;
    static public final String DEVICEINFO_XSD_URI;
    static public final String APPCONFIG_XSD_URI;
    static public final String PLUGINIFO_XSD_URI;
    
    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    static
    {
        String packageName = XSDConstants.class.getPackage().getName();
        packageName = "/" + packageName.replaceAll( "\\.", "/" );

        MIDISPEC_XSD_URI   = packageName + "/MIDISpecification.xsd";
        DEVICEINFO_XSD_URI = packageName + "/DeviceInfo.xsd";
        APPCONFIG_XSD_URI  = packageName + "/ApplicationConfig.xsd";
        PLUGINIFO_XSD_URI  = packageName + "/PluginInfo.xsd";
    }

    private XSDConstants() {}

}
