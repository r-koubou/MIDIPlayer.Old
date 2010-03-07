
package org.rz.midiplayer.xmlmodule.midispec;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.rz.midiplayer.xmlmodule.midispec package. 
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

    private final static QName _Spec_QNAME = new QName("", "spec");
    private final static QName _Drumpart_QNAME = new QName("", "drumpart");
    private final static QName _Drumpartsysexec_QNAME = new QName("", "drumpartsysexec");
    private final static QName _Reset_QNAME = new QName("", "reset");
    private final static QName _Drumpartpc_QNAME = new QName("", "drumpartpc");
    private final static QName _Melopartsysexec_QNAME = new QName("", "melopartsysexec");
    private final static QName _Melopart_QNAME = new QName("", "melopart");
    private final static QName _Melopartpc_QNAME = new QName("", "melopartpc");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.rz.midiplayer.xmlmodule.midispec
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link MeloPartSysExec }
     * 
     */
    public MeloPartSysExec createMeloPartSysExec() {
        return new MeloPartSysExec();
    }

    /**
     * Create an instance of {@link DrumPartPc }
     * 
     */
    public DrumPartPc createDrumPartPc() {
        return new DrumPartPc();
    }

    /**
     * Create an instance of {@link DrumPartSysExec }
     * 
     */
    public DrumPartSysExec createDrumPartSysExec() {
        return new DrumPartSysExec();
    }

    /**
     * Create an instance of {@link MeloPartPc }
     * 
     */
    public MeloPartPc createMeloPartPc() {
        return new MeloPartPc();
    }

    /**
     * Create an instance of {@link MeloPart }
     * 
     */
    public MeloPart createMeloPart() {
        return new MeloPart();
    }

    /**
     * Create an instance of {@link Reset }
     * 
     */
    public Reset createReset() {
        return new Reset();
    }

    /**
     * Create an instance of {@link DrumPart }
     * 
     */
    public DrumPart createDrumPart() {
        return new DrumPart();
    }

    /**
     * Create an instance of {@link Spec }
     * 
     */
    public Spec createSpec() {
        return new Spec();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Spec }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "spec")
    public JAXBElement<Spec> createSpec(Spec value) {
        return new JAXBElement<Spec>(_Spec_QNAME, Spec.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DrumPart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "drumpart")
    public JAXBElement<DrumPart> createDrumpart(DrumPart value) {
        return new JAXBElement<DrumPart>(_Drumpart_QNAME, DrumPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DrumPartSysExec }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "drumpartsysexec")
    public JAXBElement<DrumPartSysExec> createDrumpartsysexec(DrumPartSysExec value) {
        return new JAXBElement<DrumPartSysExec>(_Drumpartsysexec_QNAME, DrumPartSysExec.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reset }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "reset")
    public JAXBElement<Reset> createReset(Reset value) {
        return new JAXBElement<Reset>(_Reset_QNAME, Reset.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DrumPartPc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "drumpartpc")
    public JAXBElement<DrumPartPc> createDrumpartpc(DrumPartPc value) {
        return new JAXBElement<DrumPartPc>(_Drumpartpc_QNAME, DrumPartPc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeloPartSysExec }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "melopartsysexec")
    public JAXBElement<MeloPartSysExec> createMelopartsysexec(MeloPartSysExec value) {
        return new JAXBElement<MeloPartSysExec>(_Melopartsysexec_QNAME, MeloPartSysExec.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeloPart }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "melopart")
    public JAXBElement<MeloPart> createMelopart(MeloPart value) {
        return new JAXBElement<MeloPart>(_Melopart_QNAME, MeloPart.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MeloPartPc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "melopartpc")
    public JAXBElement<MeloPartPc> createMelopartpc(MeloPartPc value) {
        return new JAXBElement<MeloPartPc>(_Melopartpc_QNAME, MeloPartPc.class, null, value);
    }

}
