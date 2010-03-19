
package org.rz.midiplayerplugin.renderer.pianoroll3d.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Renderer complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Renderer">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}camera"/>
 *       &lt;/sequence>
 *       &lt;attribute name="viewGrid" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="viewPosition" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Renderer", propOrder = {
    "camera"
})
public class Renderer {

    @XmlElement(required = true)
    protected Camera camera;
    @XmlAttribute
    protected Boolean viewGrid;
    @XmlAttribute
    protected Boolean viewPosition;

    /**
     * Gets the value of the camera property.
     * 
     * @return
     *     possible object is
     *     {@link Camera }
     *     
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     * Sets the value of the camera property.
     * 
     * @param value
     *     allowed object is
     *     {@link Camera }
     *     
     */
    public void setCamera(Camera value) {
        this.camera = value;
    }

    /**
     * Gets the value of the viewGrid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isViewGrid() {
        if (viewGrid == null) {
            return true;
        } else {
            return viewGrid;
        }
    }

    /**
     * Sets the value of the viewGrid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setViewGrid(Boolean value) {
        this.viewGrid = value;
    }

    /**
     * Gets the value of the viewPosition property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isViewPosition() {
        if (viewPosition == null) {
            return false;
        } else {
            return viewPosition;
        }
    }

    /**
     * Sets the value of the viewPosition property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setViewPosition(Boolean value) {
        this.viewPosition = value;
    }

}
