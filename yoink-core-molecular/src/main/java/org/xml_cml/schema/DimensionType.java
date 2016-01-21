//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.29 at 10:54:28 PM EST 
//

package org.xml_cml.schema;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for dimensionType.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * <p>
 * 
 * <pre>
 * &lt;simpleType name="dimensionType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="mass"/>
 *     &lt;enumeration value="length"/>
 *     &lt;enumeration value="time"/>
 *     &lt;enumeration value="current"/>
 *     &lt;enumeration value="amount"/>
 *     &lt;enumeration value="luminosity"/>
 *     &lt;enumeration value="temperature"/>
 *     &lt;enumeration value="dimensionless"/>
 *     &lt;enumeration value="angle"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "dimensionType", namespace = "http://www.xml-cml.org/schema")
@XmlEnum
public enum DimensionType {

	@XmlEnumValue("mass")
	MASS("mass"), @XmlEnumValue("length")
	LENGTH("length"), @XmlEnumValue("time")
	TIME("time"), @XmlEnumValue("current")
	CURRENT("current"), @XmlEnumValue("amount")
	AMOUNT("amount"), @XmlEnumValue("luminosity")
	LUMINOSITY("luminosity"), @XmlEnumValue("temperature")
	TEMPERATURE("temperature"), @XmlEnumValue("dimensionless")
	DIMENSIONLESS("dimensionless"),

	/**
	 * 
	 * 
	 * <pre>
	 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="summary"&gt;An angl.&lt;/h:div&gt;
	 * </pre>
	 * 
	 * 
	 * <pre>
	 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;h:div xmlns:h="http://www.w3.org/1999/xhtml" xmlns="http://www.xml-cml.org/schema" xmlns:xsd="http://www.w3.org/2001/XMLSchema" class="description"&gt;(formally dimensionless, but useful to have units).&lt;/h:div&gt;
	 * </pre>
	 * 
	 * 
	 * 
	 */
	@XmlEnumValue("angle")
	ANGLE("angle");
	private final String value;

	DimensionType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}

	public static DimensionType fromValue(String v) {
		for (DimensionType c : DimensionType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}

}
