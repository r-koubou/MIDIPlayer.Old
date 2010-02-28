
package org.rz.midiplayer.xmlmodule.deviceinfo;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InstrumentMap complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InstrumentMap">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}instrument" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="type" type="{}instType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstrumentMap", propOrder = {
    "instrument"
})
public class InstrumentMap {

    protected List<Instrument> instrument;
    @XmlAttribute
    protected InstType type;

    /**
     * Gets the value of the instrument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instrument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInstrument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Instrument }
     * 
     * 
     */
    public List<Instrument> getInstrument() {
        if (instrument == null) {
            instrument = new ArrayList<Instrument>();
        }
        return this.instrument;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link InstType }
     *     
     */
    public InstType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstType }
     *     
     */
    public void setType(InstType value) {
        this.type = value;
    }

}
