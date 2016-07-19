//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.22 at 05:23:04 PM EST 
//


package org.gradle.pdbml.v40.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * The PDBX_NONPOLY_SCHEME category provides residue level nomenclature 
 * mapping for non-polymer entities.
 * 
 *     Example 1 - 
 * <PDBx:pdbx_nonpoly_schemeCategory>
 *    <PDBx:pdbx_nonpoly_scheme asym_id="C" ndb_seq_num="100">
 *       <PDBx:auth_mon_id>HOH</PDBx:auth_mon_id>
 *       <PDBx:auth_seq_num>100</PDBx:auth_seq_num>
 *       <PDBx:entity_id>3</PDBx:entity_id>
 *       <PDBx:mon_id>HOH</PDBx:mon_id>
 *       <PDBx:pdb_ins_code xsi:nil="true" />
 *       <PDBx:pdb_mon_id>HOH</PDBx:pdb_mon_id>
 *       <PDBx:pdb_seq_num>100</PDBx:pdb_seq_num>
 *       <PDBx:pdb_strand_id>C</PDBx:pdb_strand_id>
 *    </PDBx:pdbx_nonpoly_scheme>
 * </PDBx:pdbx_nonpoly_schemeCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_nonpoly_schemeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_nonpoly_schemeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_nonpoly_scheme" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="auth_mon_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="auth_seq_num" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="entity_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="mon_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pdb_ins_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pdb_mon_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pdb_seq_num" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pdb_strand_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="asym_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ndb_seq_num" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "pdbx_nonpoly_schemeType", propOrder = {
    "pdbxNonpolyScheme"
})
public class PdbxNonpolySchemeType {

    @XmlElement(name = "pdbx_nonpoly_scheme")
    protected List<PdbxNonpolySchemeType.PdbxNonpolyScheme> pdbxNonpolyScheme;

    /**
     * Gets the value of the pdbxNonpolyScheme property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxNonpolyScheme property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxNonpolyScheme().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxNonpolySchemeType.PdbxNonpolyScheme }
     * 
     * 
     */
    public List<PdbxNonpolySchemeType.PdbxNonpolyScheme> getPdbxNonpolyScheme() {
        if (pdbxNonpolyScheme == null) {
            pdbxNonpolyScheme = new ArrayList<PdbxNonpolySchemeType.PdbxNonpolyScheme>();
        }
        return this.pdbxNonpolyScheme;
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
     *       &lt;all>
     *         &lt;element name="auth_mon_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="auth_seq_num" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="entity_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="mon_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pdb_ins_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pdb_mon_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pdb_seq_num" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pdb_strand_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="asym_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ndb_seq_num" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {

    })
    public static class PdbxNonpolyScheme {

        @XmlElementRef(name = "auth_mon_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> authMonId;
        @XmlElementRef(name = "auth_seq_num", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> authSeqNum;
        @XmlElementRef(name = "entity_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> entityId;
        @XmlElementRef(name = "mon_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> monId;
        @XmlElementRef(name = "pdb_ins_code", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> pdbInsCode;
        @XmlElementRef(name = "pdb_mon_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> pdbMonId;
        @XmlElementRef(name = "pdb_seq_num", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> pdbSeqNum;
        @XmlElementRef(name = "pdb_strand_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> pdbStrandId;
        @XmlAttribute(name = "asym_id", required = true)
        protected String asymId;
        @XmlAttribute(name = "ndb_seq_num", required = true)
        protected String ndbSeqNum;

        /**
         * Gets the value of the authMonId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getAuthMonId() {
            return authMonId;
        }

        /**
         * Sets the value of the authMonId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setAuthMonId(JAXBElement<String> value) {
            this.authMonId = value;
        }

        /**
         * Gets the value of the authSeqNum property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getAuthSeqNum() {
            return authSeqNum;
        }

        /**
         * Sets the value of the authSeqNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setAuthSeqNum(JAXBElement<String> value) {
            this.authSeqNum = value;
        }

        /**
         * Gets the value of the entityId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getEntityId() {
            return entityId;
        }

        /**
         * Sets the value of the entityId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setEntityId(JAXBElement<String> value) {
            this.entityId = value;
        }

        /**
         * Gets the value of the monId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getMonId() {
            return monId;
        }

        /**
         * Sets the value of the monId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setMonId(JAXBElement<String> value) {
            this.monId = value;
        }

        /**
         * Gets the value of the pdbInsCode property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPdbInsCode() {
            return pdbInsCode;
        }

        /**
         * Sets the value of the pdbInsCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPdbInsCode(JAXBElement<String> value) {
            this.pdbInsCode = value;
        }

        /**
         * Gets the value of the pdbMonId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPdbMonId() {
            return pdbMonId;
        }

        /**
         * Sets the value of the pdbMonId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPdbMonId(JAXBElement<String> value) {
            this.pdbMonId = value;
        }

        /**
         * Gets the value of the pdbSeqNum property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPdbSeqNum() {
            return pdbSeqNum;
        }

        /**
         * Sets the value of the pdbSeqNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPdbSeqNum(JAXBElement<String> value) {
            this.pdbSeqNum = value;
        }

        /**
         * Gets the value of the pdbStrandId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPdbStrandId() {
            return pdbStrandId;
        }

        /**
         * Sets the value of the pdbStrandId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPdbStrandId(JAXBElement<String> value) {
            this.pdbStrandId = value;
        }

        /**
         * Gets the value of the asymId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAsymId() {
            return asymId;
        }

        /**
         * Sets the value of the asymId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAsymId(String value) {
            this.asymId = value;
        }

        /**
         * Gets the value of the ndbSeqNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNdbSeqNum() {
            return ndbSeqNum;
        }

        /**
         * Sets the value of the ndbSeqNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNdbSeqNum(String value) {
            this.ndbSeqNum = value;
        }

    }

}
