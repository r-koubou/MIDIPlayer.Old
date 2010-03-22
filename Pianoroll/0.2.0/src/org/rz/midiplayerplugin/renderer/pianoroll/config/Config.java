
package org.rz.midiplayerplugin.renderer.pianoroll.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Config complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Config">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}pianoroll"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Config", propOrder = {
    "pianoroll"
})
public class Config {

    @XmlElement(required = true)
    protected Pianoroll pianoroll;

    /**
     * Gets the value of the pianoroll property.
     * 
     * @return
     *     possible object is
     *     {@link Pianoroll }
     *     
     */
    public Pianoroll getPianoroll() {
        return pianoroll;
    }

    /**
     * Sets the value of the pianoroll property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pianoroll }
     *     
     */
    public void setPianoroll(Pianoroll value) {
        this.pianoroll = value;
    }

}
