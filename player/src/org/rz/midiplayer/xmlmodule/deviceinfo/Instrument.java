
package org.rz.midiplayer.xmlmodule.deviceinfo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Instrument complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Instrument">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="pc" type="{}_7bitInt" />
 *       &lt;attribute name="msb" type="{}_7bitInt" />
 *       &lt;attribute name="lsb" type="{}_7bitInt" />
 *       &lt;attribute name="mapName" type="{http://www.w3.org/2001/XMLSchema}string" default="" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Instrument")
public class Instrument {

    @XmlAttribute
    protected Integer pc;
    @XmlAttribute
    protected Integer msb;
    @XmlAttribute
    protected Integer lsb;
    @XmlAttribute
    protected String mapName;
    @XmlAttribute
    protected String name;

    /**
     * Gets the value of the pc property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPc() {
        return pc;
    }

    /**
     * Sets the value of the pc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPc(Integer value) {
        this.pc = value;
    }

    /**
     * Gets the value of the msb property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMsb() {
        return msb;
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
    public Integer getLsb() {
        return lsb;
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

    /**
     * Gets the value of the mapName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMapName() {
        if (mapName == null) {
            return "";
        } else {
            return mapName;
        }
    }

    /**
     * Sets the value of the mapName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMapName(String value) {
        this.mapName = value;
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
