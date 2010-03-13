
package org.rz.midiplayer.xmlmodule.deviceinfo;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.rz.midiplayer.xmlmodule.deviceinfo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Map_QNAME = new QName("", "map");
    private final static QName _Deviceinfo_QNAME = new QName("", "deviceinfo");
    private final static QName _Import_QNAME = new QName("", "import");
    private final static QName _Instrument_QNAME = new QName("", "instrument");
    private final static QName _Instruments_QNAME = new QName("", "instruments");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.rz.midiplayer.xmlmodule.deviceinfo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Instrument }
     * 
     */
    public Instrument createInstrument() {
        return new Instrument();
    }

    /**
     * Create an instance of {@link InstrumentMap }
     * 
     */
    public InstrumentMap createInstrumentMap() {
        return new InstrumentMap();
    }

    /**
     * Create an instance of {@link DeviceInfo }
     * 
     */
    public DeviceInfo createDeviceInfo() {
        return new DeviceInfo();
    }

    /**
     * Create an instance of {@link ImportInfo }
     * 
     */
    public ImportInfo createImportInfo() {
        return new ImportInfo();
    }

    /**
     * Create an instance of {@link InstrumentList }
     * 
     */
    public InstrumentList createInstrumentList() {
        return new InstrumentList();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InstrumentMap }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "map")
    public JAXBElement<InstrumentMap> createMap(InstrumentMap value) {
        return new JAXBElement<InstrumentMap>(_Map_QNAME, InstrumentMap.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "deviceinfo")
    public JAXBElement<DeviceInfo> createDeviceinfo(DeviceInfo value) {
        return new JAXBElement<DeviceInfo>(_Deviceinfo_QNAME, DeviceInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "import")
    public JAXBElement<ImportInfo> createImport(ImportInfo value) {
        return new JAXBElement<ImportInfo>(_Import_QNAME, ImportInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Instrument }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "instrument")
    public JAXBElement<Instrument> createInstrument(Instrument value) {
        return new JAXBElement<Instrument>(_Instrument_QNAME, Instrument.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InstrumentList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "instruments")
    public JAXBElement<InstrumentList> createInstruments(InstrumentList value) {
        return new JAXBElement<InstrumentList>(_Instruments_QNAME, InstrumentList.class, null, value);
    }

}
