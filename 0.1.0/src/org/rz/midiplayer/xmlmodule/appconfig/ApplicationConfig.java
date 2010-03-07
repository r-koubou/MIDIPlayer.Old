
package org.rz.midiplayer.xmlmodule.appconfig;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ApplicationConfig complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationConfig">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}devicefile"/>
 *         &lt;element ref="{}midiout"/>
 *         &lt;element ref="{}midiin"/>
 *         &lt;element ref="{}lastdirectory"/>
 *         &lt;element ref="{}plugin"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationConfig", propOrder = {
    "devicefile",
    "midiout",
    "midiin",
    "lastdirectory",
    "plugin"
})
public class ApplicationConfig {

    @XmlElement(required = true)
    protected Devicefile devicefile;
    @XmlElement(required = true)
    protected Midiout midiout;
    @XmlElement(required = true)
    protected MidiIn midiin;
    @XmlElement(required = true)
    protected Lastdirectory lastdirectory;
    @XmlElement(required = true)
    protected Plugin plugin;

    /**
     * Gets the value of the devicefile property.
     * 
     * @return
     *     possible object is
     *     {@link Devicefile }
     *     
     */
    public Devicefile getDevicefile() {
        return devicefile;
    }

    /**
     * Sets the value of the devicefile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Devicefile }
     *     
     */
    public void setDevicefile(Devicefile value) {
        this.devicefile = value;
    }

    /**
     * Gets the value of the midiout property.
     * 
     * @return
     *     possible object is
     *     {@link Midiout }
     *     
     */
    public Midiout getMidiout() {
        return midiout;
    }

    /**
     * Sets the value of the midiout property.
     * 
     * @param value
     *     allowed object is
     *     {@link Midiout }
     *     
     */
    public void setMidiout(Midiout value) {
        this.midiout = value;
    }

    /**
     * Gets the value of the midiin property.
     * 
     * @return
     *     possible object is
     *     {@link MidiIn }
     *     
     */
    public MidiIn getMidiin() {
        return midiin;
    }

    /**
     * Sets the value of the midiin property.
     * 
     * @param value
     *     allowed object is
     *     {@link MidiIn }
     *     
     */
    public void setMidiin(MidiIn value) {
        this.midiin = value;
    }

    /**
     * Gets the value of the lastdirectory property.
     * 
     * @return
     *     possible object is
     *     {@link Lastdirectory }
     *     
     */
    public Lastdirectory getLastdirectory() {
        return lastdirectory;
    }

    /**
     * Sets the value of the lastdirectory property.
     * 
     * @param value
     *     allowed object is
     *     {@link Lastdirectory }
     *     
     */
    public void setLastdirectory(Lastdirectory value) {
        this.lastdirectory = value;
    }

    /**
     * Gets the value of the plugin property.
     * 
     * @return
     *     possible object is
     *     {@link Plugin }
     *     
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * Sets the value of the plugin property.
     * 
     * @param value
     *     allowed object is
     *     {@link Plugin }
     *     
     */
    public void setPlugin(Plugin value) {
        this.plugin = value;
    }

}
