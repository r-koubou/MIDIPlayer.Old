
package org.rz.midiplayer.xmlmodule.midispec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MeloPartPc complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MeloPartPc">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute name="ch" type="{}m1To127Int" default="-1" />
 *       &lt;attribute name="msb" type="{}m1To127Int" default="-1" />
 *       &lt;attribute name="lsb" type="{}m1To127Int" default="-1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeloPartPc")
public class MeloPartPc {

    @XmlAttribute
    protected Integer ch;
    @XmlAttribute
    protected Integer msb;
    @XmlAttribute
    protected Integer lsb;

    /**
     * Gets the value of the ch property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getCh() {
        if (ch == null) {
            return -1;
        } else {
            return ch;
        }
    }

    /**
     * Sets the value of the ch property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setCh(Integer value) {
        this.ch = value;
    }

    /**
     * Gets the value of the msb property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMsb() {
        if (msb == null) {
            return -1;
        } else {
            return msb;
        }
    }

    /**
     * Sets the value of the msb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMsb(Integer value) {
        this.msb = value;
    }

    /**
     * Gets the value of the lsb property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getLsb() {
        if (lsb == null) {
            return -1;
        } else {
            return lsb;
        }
    }

    /**
     * Sets the value of the lsb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setLsb(Integer value) {
        this.lsb = value;
    }

}
