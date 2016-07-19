//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.22 at 05:23:04 PM EST 
//


package org.gradle.pdbml.v40.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Data items in the DATABASE_2 category record details about the
 * database identifiers of the data block.
 * 
 * These data items are assigned by database managers and should
 * only appear in a data block if they originate from that source.
 * 
 * The name of this category, DATABASE_2, arose because the
 * category name DATABASE was already in use in the core CIF
 * dictionary, but was used differently from the way it needed
 * to be used in the mmCIF dictionary. Since CIF data names
 * cannot be changed once they have been adopted, a new category
 * had to be created.
 * 
 *     Example 1 - based on PDB entry 5HVP and laboratory records for the
 *                 structure corresponding to PDB entry 5HVP.
 * <PDBx:database_2Category>
 *    <PDBx:database_2 database_code="5HVP" database_id="PDB"></PDBx:database_2>
 * </PDBx:database_2Category>
 * 
 * 
 *          
 * 
 * <p>Java class for database_2Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="database_2Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="database_2" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="database_code" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="database_id" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="CAS"/>
 *                       &lt;enumeration value="CSD"/>
 *                       &lt;enumeration value="EMDB"/>
 *                       &lt;enumeration value="ICSD"/>
 *                       &lt;enumeration value="MDF"/>
 *                       &lt;enumeration value="NDB"/>
 *                       &lt;enumeration value="NBS"/>
 *                       &lt;enumeration value="PDB"/>
 *                       &lt;enumeration value="PDF"/>
 *                       &lt;enumeration value="RCSB"/>
 *                       &lt;enumeration value="EBI"/>
 *                       &lt;enumeration value="PDBE"/>
 *                       &lt;enumeration value="BMRB"/>
 *                       &lt;enumeration value="WWPDB"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "database_2Type", propOrder = {
    "database2"
})
public class Database2Type {

    @XmlElement(name = "database_2")
    protected List<Database2Type.Database2> database2;

    /**
     * Gets the value of the database2 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the database2 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatabase2().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Database2Type.Database2 }
     * 
     * 
     */
    public List<Database2Type.Database2> getDatabase2() {
        if (database2 == null) {
            database2 = new ArrayList<Database2Type.Database2>();
        }
        return this.database2;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="database_code" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="database_id" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="CAS"/>
     *             &lt;enumeration value="CSD"/>
     *             &lt;enumeration value="EMDB"/>
     *             &lt;enumeration value="ICSD"/>
     *             &lt;enumeration value="MDF"/>
     *             &lt;enumeration value="NDB"/>
     *             &lt;enumeration value="NBS"/>
     *             &lt;enumeration value="PDB"/>
     *             &lt;enumeration value="PDF"/>
     *             &lt;enumeration value="RCSB"/>
     *             &lt;enumeration value="EBI"/>
     *             &lt;enumeration value="PDBE"/>
     *             &lt;enumeration value="BMRB"/>
     *             &lt;enumeration value="WWPDB"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Database2 {

        @XmlAttribute(name = "database_code", required = true)
        protected String databaseCode;
        @XmlAttribute(name = "database_id", required = true)
        protected String databaseId;

        /**
         * Gets the value of the databaseCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDatabaseCode() {
            return databaseCode;
        }

        /**
         * Sets the value of the databaseCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDatabaseCode(String value) {
            this.databaseCode = value;
        }

        /**
         * Gets the value of the databaseId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDatabaseId() {
            return databaseId;
        }

        /**
         * Sets the value of the databaseId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDatabaseId(String value) {
            this.databaseId = value;
        }

    }

}
