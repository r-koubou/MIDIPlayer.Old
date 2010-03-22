
package org.rz.midiplayerplugin.renderer.pianoroll3d.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Position complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Position">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="tx" type="{http://www.w3.org/2001/XMLSchema}float" default="0.0" />
 *       &lt;attribute name="ty" type="{http://www.w3.org/2001/XMLSchema}float" default="0.0" />
 *       &lt;attribute name="tz" type="{http://www.w3.org/2001/XMLSchema}float" default="0.0" />
 *       &lt;attribute name="rx" type="{http://www.w3.org/2001/XMLSchema}float" default="0.0" />
 *       &lt;attribute name="ry" type="{http://www.w3.org/2001/XMLSchema}float" default="0.0" />
 *       &lt;attribute name="rz" type="{http://www.w3.org/2001/XMLSchema}float" default="0.0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Position")
public class Position {

    @XmlAttribute
    protected Float tx;
    @XmlAttribute
    protected Float ty;
    @XmlAttribute
    protected Float tz;
    @XmlAttribute
    protected Float rx;
    @XmlAttribute
    protected Float ry;
    @XmlAttribute
    protected Float rz;

    /**
     * Gets the value of the tx property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public float getTx() {
        if (tx == null) {
            return  0.0F;
        } else {
            return tx;
        }
    }

    /**
     * Sets the value of the tx property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTx(Float value) {
        this.tx = value;
    }

    /**
     * Gets the value of the ty property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public float getTy() {
        if (ty == null) {
            return  0.0F;
        } else {
            return ty;
        }
    }

    /**
     * Sets the value of the ty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTy(Float value) {
        this.ty = value;
    }

    /**
     * Gets the value of the tz property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public float getTz() {
        if (tz == null) {
            return  0.0F;
        } else {
            return tz;
        }
    }

    /**
     * Sets the value of the tz property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setTz(Float value) {
        this.tz = value;
    }

    /**
     * Gets the value of the rx property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public float getRx() {
        if (rx == null) {
            return  0.0F;
        } else {
            return rx;
        }
    }

    /**
     * Sets the value of the rx property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setRx(Float value) {
        this.rx = value;
    }

    /**
     * Gets the value of the ry property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public float getRy() {
        if (ry == null) {
            return  0.0F;
        } else {
            return ry;
        }
    }

    /**
     * Sets the value of the ry property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setRy(Float value) {
        this.ry = value;
    }

    /**
     * Gets the value of the rz property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public float getRz() {
        if (rz == null) {
            return  0.0F;
        } else {
            return rz;
        }
    }

    /**
     * Sets the value of the rz property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setRz(Float value) {
        this.rz = value;
    }

}
