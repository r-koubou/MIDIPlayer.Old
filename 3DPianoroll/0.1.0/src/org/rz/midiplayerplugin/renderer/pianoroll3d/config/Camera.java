
package org.rz.midiplayerplugin.renderer.pianoroll3d.config;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Camera complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Camera">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}position" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="lastUsed" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Camera", propOrder = {
    "position"
})
public class Camera {

    @XmlElement(required = true)
    protected List<Position> position;
    @XmlAttribute
    @XmlSchemaType(name = "unsignedByte")
    protected Short lastUsed;

    /**
     * Gets the value of the position property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the position property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPosition().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Position }
     * 
     * 
     */
    public List<Position> getPosition() {
        if (position == null) {
            position = new ArrayList<Position>();
        }
        return this.position;
    }

    /**
     * Gets the value of the lastUsed property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getLastUsed() {
        if (lastUsed == null) {
            return ((short) 0);
        } else {
            return lastUsed;
        }
    }

    /**
     * Sets the value of the lastUsed property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setLastUsed(Short value) {
        this.lastUsed = value;
    }

}
