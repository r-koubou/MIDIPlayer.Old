
package org.rz.midiplayer.xmlmodule.midispec;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DrumPart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DrumPart">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}drumpartsysexec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}drumpartpc" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *       &lt;attribute name="default" type="{}_4bitInt" default="10" />
 *       &lt;attribute name="defaultpc" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *       &lt;attribute name="defaultmsb" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *       &lt;attribute name="defaultlsb" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DrumPart", propOrder = {
    "drumpartsysexec",
    "drumpartpc"
})
public class DrumPart {

    protected List<DrumPartSysExec> drumpartsysexec;
    protected List<DrumPartPc> drumpartpc;
    @XmlAttribute(name = "default")
    protected Integer _default;
    @XmlAttribute
    protected Integer defaultpc;
    @XmlAttribute
    protected Integer defaultmsb;
    @XmlAttribute
    protected Integer defaultlsb;

    /**
     * Gets the value of the drumpartsysexec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drumpartsysexec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrumpartsysexec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DrumPartSysExec }
     * 
     * 
     */
    public List<DrumPartSysExec> getDrumpartsysexec() {
        if (drumpartsysexec == null) {
            drumpartsysexec = new ArrayList<DrumPartSysExec>();
        }
        return this.drumpartsysexec;
    }

    /**
     * Gets the value of the drumpartpc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the drumpartpc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDrumpartpc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DrumPartPc }
     * 
     * 
     */
    public List<DrumPartPc> getDrumpartpc() {
        if (drumpartpc == null) {
            drumpartpc = new ArrayList<DrumPartPc>();
        }
        return this.drumpartpc;
    }

    /**
     * Gets the value of the default property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getDefault() {
        if (_default == null) {
            return  10;
        } else {
            return _default;
        }
    }

    /**
     * Sets the value of the default property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefault(Integer value) {
        this._default = value;
    }

    /**
     * Gets the value of the defaultpc property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getDefaultpc() {
        if (defaultpc == null) {
            return  0;
        } else {
            return defaultpc;
        }
    }

    /**
     * Sets the value of the defaultpc property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefaultpc(Integer value) {
        this.defaultpc = value;
    }

    /**
     * Gets the value of the defaultmsb property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getDefaultmsb() {
        if (defaultmsb == null) {
            return  0;
        } else {
            return defaultmsb;
        }
    }

    /**
     * Sets the value of the defaultmsb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefaultmsb(Integer value) {
        this.defaultmsb = value;
    }

    /**
     * Gets the value of the defaultlsb property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getDefaultlsb() {
        if (defaultlsb == null) {
            return  0;
        } else {
            return defaultlsb;
        }
    }

    /**
     * Sets the value of the defaultlsb property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDefaultlsb(Integer value) {
        this.defaultlsb = value;
    }

}
