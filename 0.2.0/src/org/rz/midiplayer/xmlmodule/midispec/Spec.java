
package org.rz.midiplayer.xmlmodule.midispec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Spec complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Spec">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}reset"/>
 *         &lt;element ref="{}melopart"/>
 *         &lt;element ref="{}drumpart"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Spec", propOrder = {
    "reset",
    "melopart",
    "drumpart"
})
public class Spec {

    @XmlElement(required = true)
    protected Reset reset;
    @XmlElement(required = true)
    protected MeloPart melopart;
    @XmlElement(required = true)
    protected DrumPart drumpart;
    @XmlAttribute(required = true)
    protected String name;

    /**
     * Gets the value of the reset property.
     * 
     * @return
     *     possible object is
     *     {@link Reset }
     *     
     */
    public Reset getReset() {
        return reset;
    }

    /**
     * Sets the value of the reset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Reset }
     *     
     */
    public void setReset(Reset value) {
        this.reset = value;
    }

    /**
     * Gets the value of the melopart property.
     * 
     * @return
     *     possible object is
     *     {@link MeloPart }
     *     
     */
    public MeloPart getMelopart() {
        return melopart;
    }

    /**
     * Sets the value of the melopart property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeloPart }
     *     
     */
    public void setMelopart(MeloPart value) {
        this.melopart = value;
    }

    /**
     * Gets the value of the drumpart property.
     * 
     * @return
     *     possible object is
     *     {@link DrumPart }
     *     
     */
    public DrumPart getDrumpart() {
        return drumpart;
    }

    /**
     * Sets the value of the drumpart property.
     * 
     * @param value
     *     allowed object is
     *     {@link DrumPart }
     *     
     */
    public void setDrumpart(DrumPart value) {
        this.drumpart = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

}
