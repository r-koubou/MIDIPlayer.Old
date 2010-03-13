
package org.rz.midiplayer.xmlmodule.midispec;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Reset complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Reset">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *       &lt;/sequence>
 *       &lt;attribute ref="{}sysexec use="required""/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Reset")
public class Reset {

    @XmlAttribute(required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] sysexec;

    /**
     * Gets the value of the sysexec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getSysexec() {
        return sysexec;
    }

    /**
     * Sets the value of the sysexec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysexec(byte[] value) {
        this.sysexec = ((byte[]) value);
    }

}
