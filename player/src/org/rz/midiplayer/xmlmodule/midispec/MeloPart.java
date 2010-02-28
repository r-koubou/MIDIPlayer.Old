
package org.rz.midiplayer.xmlmodule.midispec;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MeloPart complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MeloPart">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{}melopartsysexec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}melopartpc" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeloPart", propOrder = {
    "melopartsysexec",
    "melopartpc"
})
public class MeloPart {

    protected List<MeloPartSysExec> melopartsysexec;
    protected List<MeloPartPc> melopartpc;

    /**
     * Gets the value of the melopartsysexec property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the melopartsysexec property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMelopartsysexec().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeloPartSysExec }
     * 
     * 
     */
    public List<MeloPartSysExec> getMelopartsysexec() {
        if (melopartsysexec == null) {
            melopartsysexec = new ArrayList<MeloPartSysExec>();
        }
        return this.melopartsysexec;
    }

    /**
     * Gets the value of the melopartpc property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the melopartpc property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMelopartpc().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MeloPartPc }
     * 
     * 
     */
    public List<MeloPartPc> getMelopartpc() {
        if (melopartpc == null) {
            melopartpc = new ArrayList<MeloPartPc>();
        }
        return this.melopartpc;
    }

}
