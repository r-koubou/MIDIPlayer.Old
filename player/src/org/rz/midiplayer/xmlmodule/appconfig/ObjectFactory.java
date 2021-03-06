
package org.rz.midiplayer.xmlmodule.appconfig;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.rz.midiplayer.xmlmodule.appconfig package. 
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

    private final static QName _Lastdirectory_QNAME = new QName("", "lastdirectory");
    private final static QName _Devicefile_QNAME = new QName("", "devicefile");
    private final static QName _Config_QNAME = new QName("", "config");
    private final static QName _Midiout_QNAME = new QName("", "midiout");
    private final static QName _Renderer_QNAME = new QName("", "renderer");
    private final static QName _Plugin_QNAME = new QName("", "plugin");
    private final static QName _Midiin_QNAME = new QName("", "midiin");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.rz.midiplayer.xmlmodule.appconfig
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Renderer }
     * 
     */
    public Renderer createRenderer() {
        return new Renderer();
    }

    /**
     * Create an instance of {@link MidiIn }
     * 
     */
    public MidiIn createMidiIn() {
        return new MidiIn();
    }

    /**
     * Create an instance of {@link Lastdirectory }
     * 
     */
    public Lastdirectory createLastdirectory() {
        return new Lastdirectory();
    }

    /**
     * Create an instance of {@link Midiout }
     * 
     */
    public Midiout createMidiout() {
        return new Midiout();
    }

    /**
     * Create an instance of {@link Devicefile }
     * 
     */
    public Devicefile createDevicefile() {
        return new Devicefile();
    }

    /**
     * Create an instance of {@link ApplicationConfig }
     * 
     */
    public ApplicationConfig createApplicationConfig() {
        return new ApplicationConfig();
    }

    /**
     * Create an instance of {@link Plugin }
     * 
     */
    public Plugin createPlugin() {
        return new Plugin();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Lastdirectory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "lastdirectory")
    public JAXBElement<Lastdirectory> createLastdirectory(Lastdirectory value) {
        return new JAXBElement<Lastdirectory>(_Lastdirectory_QNAME, Lastdirectory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Devicefile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "devicefile")
    public JAXBElement<Devicefile> createDevicefile(Devicefile value) {
        return new JAXBElement<Devicefile>(_Devicefile_QNAME, Devicefile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ApplicationConfig }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "config")
    public JAXBElement<ApplicationConfig> createConfig(ApplicationConfig value) {
        return new JAXBElement<ApplicationConfig>(_Config_QNAME, ApplicationConfig.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Midiout }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "midiout")
    public JAXBElement<Midiout> createMidiout(Midiout value) {
        return new JAXBElement<Midiout>(_Midiout_QNAME, Midiout.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Renderer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "renderer")
    public JAXBElement<Renderer> createRenderer(Renderer value) {
        return new JAXBElement<Renderer>(_Renderer_QNAME, Renderer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Plugin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "plugin")
    public JAXBElement<Plugin> createPlugin(Plugin value) {
        return new JAXBElement<Plugin>(_Plugin_QNAME, Plugin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MidiIn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "midiin")
    public JAXBElement<MidiIn> createMidiin(MidiIn value) {
        return new JAXBElement<MidiIn>(_Midiin_QNAME, MidiIn.class, null, value);
    }

}
