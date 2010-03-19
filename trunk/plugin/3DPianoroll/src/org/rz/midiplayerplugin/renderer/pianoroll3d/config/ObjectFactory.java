
package org.rz.midiplayerplugin.renderer.pianoroll3d.config;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.rz.midiplayerplugin.renderer.pianoroll3d.config package. 
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

    private final static QName _Position_QNAME = new QName("", "position");
    private final static QName _Color_QNAME = new QName("", "color");
    private final static QName _Camera_QNAME = new QName("", "camera");
    private final static QName _Config_QNAME = new QName("", "config");
    private final static QName _Pianoroll_QNAME = new QName("", "pianoroll");
    private final static QName _Renderer_QNAME = new QName("", "renderer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.rz.midiplayerplugin.renderer.pianoroll3d.config
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Config }
     * 
     */
    public Config createConfig() {
        return new Config();
    }

    /**
     * Create an instance of {@link Pianoroll }
     * 
     */
    public Pianoroll createPianoroll() {
        return new Pianoroll();
    }

    /**
     * Create an instance of {@link Camera }
     * 
     */
    public Camera createCamera() {
        return new Camera();
    }

    /**
     * Create an instance of {@link Renderer }
     * 
     */
    public Renderer createRenderer() {
        return new Renderer();
    }

    /**
     * Create an instance of {@link Color }
     * 
     */
    public Color createColor() {
        return new Color();
    }

    /**
     * Create an instance of {@link Position }
     * 
     */
    public Position createPosition() {
        return new Position();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Position }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "position")
    public JAXBElement<Position> createPosition(Position value) {
        return new JAXBElement<Position>(_Position_QNAME, Position.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Color }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "color")
    public JAXBElement<Color> createColor(Color value) {
        return new JAXBElement<Color>(_Color_QNAME, Color.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Camera }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "camera")
    public JAXBElement<Camera> createCamera(Camera value) {
        return new JAXBElement<Camera>(_Camera_QNAME, Camera.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Config }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "config")
    public JAXBElement<Config> createConfig(Config value) {
        return new JAXBElement<Config>(_Config_QNAME, Config.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Pianoroll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pianoroll")
    public JAXBElement<Pianoroll> createPianoroll(Pianoroll value) {
        return new JAXBElement<Pianoroll>(_Pianoroll_QNAME, Pianoroll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Renderer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "renderer")
    public JAXBElement<Renderer> createRenderer(Renderer value) {
        return new JAXBElement<Renderer>(_Renderer_QNAME, Renderer.class, null, value);
    }

}
