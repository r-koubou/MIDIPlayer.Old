
package org.rz.midiplayer.xmlmodule.deviceinfo;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for instType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="instType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="melo"/>
 *     &lt;enumeration value="drum"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "instType")
@XmlEnum
public enum InstType {

    @XmlEnumValue("melo")
    MELO("melo"),
    @XmlEnumValue("drum")
    DRUM("drum");
    private final String value;

    InstType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static InstType fromValue(String v) {
        for (InstType c: InstType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
