
package org.rz.midiplayer.xmlmodule.appconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Plugin complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Plugin">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}renderer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Plugin", propOrder = {
    "renderer"
})
public class Plugin {

    @XmlElement(required = true)
    protected Renderer renderer;

    /**
     * Gets the value of the renderer property.
     * 
     * @return
     *     possible object is
     *     {@link Renderer }
     *     
     */
    public Renderer getRenderer() {
        return renderer;
    }

    /**
     * Sets the value of the renderer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Renderer }
     *     
     */
    public void setRenderer(Renderer value) {
        this.renderer = value;
    }

}
