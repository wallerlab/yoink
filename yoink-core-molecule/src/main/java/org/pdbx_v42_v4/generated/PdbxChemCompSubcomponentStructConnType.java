//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.06 at 06:37:17 PM CEST 
//


package org.pdbx_v42_v4.generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Data items in the pdbx_chem_comp_subcomponent_struct_conn
 * list the chemical interactions among the subcomponents in 
 * the chemical component.
 * 
 *     Example 1 - 
 * <PDBx:pdbx_chem_comp_subcomponent_struct_connCategory>
 *    <PDBx:pdbx_chem_comp_subcomponent_struct_conn id="1">
 *       <PDBx:atom_id_1> O4</PDBx:atom_id_1>
 *       <PDBx:atom_id_2> C1</PDBx:atom_id_2>
 *       <PDBx:comp_id_1>BGC</PDBx:comp_id_1>
 *       <PDBx:comp_id_2>BGC</PDBx:comp_id_2>
 *       <PDBx:entity_id_1>1</PDBx:entity_id_1>
 *       <PDBx:entity_id_2>1</PDBx:entity_id_2>
 *       <PDBx:seq_id_1>1</PDBx:seq_id_1>
 *       <PDBx:seq_id_2>2</PDBx:seq_id_2>
 *       <PDBx:type>covale</PDBx:type>
 *    </PDBx:pdbx_chem_comp_subcomponent_struct_conn>
 *    <PDBx:pdbx_chem_comp_subcomponent_struct_conn id="2">
 *       <PDBx:atom_id_1> O4</PDBx:atom_id_1>
 *       <PDBx:atom_id_2> C1</PDBx:atom_id_2>
 *       <PDBx:comp_id_1>BGC</PDBx:comp_id_1>
 *       <PDBx:comp_id_2>BGC</PDBx:comp_id_2>
 *       <PDBx:entity_id_1>1</PDBx:entity_id_1>
 *       <PDBx:entity_id_2>1</PDBx:entity_id_2>
 *       <PDBx:seq_id_1>2</PDBx:seq_id_1>
 *       <PDBx:seq_id_2>3</PDBx:seq_id_2>
 *       <PDBx:type>covale</PDBx:type>
 *    </PDBx:pdbx_chem_comp_subcomponent_struct_conn>
 *    <PDBx:pdbx_chem_comp_subcomponent_struct_conn id="3">
 *       <PDBx:atom_id_1> O4</PDBx:atom_id_1>
 *       <PDBx:atom_id_2> C1</PDBx:atom_id_2>
 *       <PDBx:comp_id_1>BGC</PDBx:comp_id_1>
 *       <PDBx:comp_id_2>BGC</PDBx:comp_id_2>
 *       <PDBx:entity_id_1>1</PDBx:entity_id_1>
 *       <PDBx:entity_id_2>1</PDBx:entity_id_2>
 *       <PDBx:seq_id_1>3</PDBx:seq_id_1>
 *       <PDBx:seq_id_2>4</PDBx:seq_id_2>
 *       <PDBx:type>covale</PDBx:type>
 *    </PDBx:pdbx_chem_comp_subcomponent_struct_conn>
 *    <PDBx:pdbx_chem_comp_subcomponent_struct_conn id="4">
 *       <PDBx:atom_id_1> O4</PDBx:atom_id_1>
 *       <PDBx:atom_id_2> C1</PDBx:atom_id_2>
 *       <PDBx:comp_id_1>BGC</PDBx:comp_id_1>
 *       <PDBx:comp_id_2>BGC</PDBx:comp_id_2>
 *       <PDBx:entity_id_1>1</PDBx:entity_id_1>
 *       <PDBx:entity_id_2>1</PDBx:entity_id_2>
 *       <PDBx:seq_id_1>4</PDBx:seq_id_1>
 *       <PDBx:seq_id_2>5</PDBx:seq_id_2>
 *       <PDBx:type>covale</PDBx:type>
 *    </PDBx:pdbx_chem_comp_subcomponent_struct_conn>
 *    <PDBx:pdbx_chem_comp_subcomponent_struct_conn id="5">
 *       <PDBx:atom_id_1> O4</PDBx:atom_id_1>
 *       <PDBx:atom_id_2> C1</PDBx:atom_id_2>
 *       <PDBx:comp_id_1>BGC</PDBx:comp_id_1>
 *       <PDBx:comp_id_2>BGC</PDBx:comp_id_2>
 *       <PDBx:entity_id_1>1</PDBx:entity_id_1>
 *       <PDBx:entity_id_2>1</PDBx:entity_id_2>
 *       <PDBx:seq_id_1>5</PDBx:seq_id_1>
 *       <PDBx:seq_id_2>6</PDBx:seq_id_2>
 *       <PDBx:type>covale</PDBx:type>
 *    </PDBx:pdbx_chem_comp_subcomponent_struct_conn>
 *    <PDBx:pdbx_chem_comp_subcomponent_struct_conn id="6">
 *       <PDBx:atom_id_1> O4</PDBx:atom_id_1>
 *       <PDBx:atom_id_2> C1</PDBx:atom_id_2>
 *       <PDBx:comp_id_1>BGC</PDBx:comp_id_1>
 *       <PDBx:comp_id_2>BGC</PDBx:comp_id_2>
 *       <PDBx:entity_id_1>1</PDBx:entity_id_1>
 *       <PDBx:entity_id_2>1</PDBx:entity_id_2>
 *       <PDBx:seq_id_1>6</PDBx:seq_id_1>
 *       <PDBx:seq_id_2>7</PDBx:seq_id_2>
 *       <PDBx:type>covale</PDBx:type>
 *    </PDBx:pdbx_chem_comp_subcomponent_struct_conn>
 *    <PDBx:pdbx_chem_comp_subcomponent_struct_conn id="7">
 *       <PDBx:atom_id_1> O4</PDBx:atom_id_1>
 *       <PDBx:atom_id_2> C1</PDBx:atom_id_2>
 *       <PDBx:comp_id_1>BGC</PDBx:comp_id_1>
 *       <PDBx:comp_id_2>BGC</PDBx:comp_id_2>
 *       <PDBx:entity_id_1>1</PDBx:entity_id_1>
 *       <PDBx:entity_id_2>1</PDBx:entity_id_2>
 *       <PDBx:seq_id_1>7</PDBx:seq_id_1>
 *       <PDBx:seq_id_2>8</PDBx:seq_id_2>
 *       <PDBx:type>covale</PDBx:type>
 *    </PDBx:pdbx_chem_comp_subcomponent_struct_conn>
 * </PDBx:pdbx_chem_comp_subcomponent_struct_connCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_chem_comp_subcomponent_struct_connType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_chem_comp_subcomponent_struct_connType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_chem_comp_subcomponent_struct_conn" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="atom_id_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="atom_id_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="comp_id_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="comp_id_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="entity_id_1" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="entity_id_2" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="seq_id_1" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="seq_id_2" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="type">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="covale"/>
 *                         &lt;enumeration value="disulf"/>
 *                         &lt;enumeration value="hydrog"/>
 *                         &lt;enumeration value="metalc"/>
 *                         &lt;enumeration value="mismat"/>
 *                         &lt;enumeration value="saltbr"/>
 *                         &lt;enumeration value="covale_base"/>
 *                         &lt;enumeration value="covale_sugar"/>
 *                         &lt;enumeration value="covale_phosphate"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
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
@XmlType(name = "pdbx_chem_comp_subcomponent_struct_connType", propOrder = {
    "pdbxChemCompSubcomponentStructConn"
})
public class PdbxChemCompSubcomponentStructConnType {

    @XmlElement(name = "pdbx_chem_comp_subcomponent_struct_conn")
    protected List<PdbxChemCompSubcomponentStructConnType.PdbxChemCompSubcomponentStructConn> pdbxChemCompSubcomponentStructConn;

    /**
     * Gets the value of the pdbxChemCompSubcomponentStructConn property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxChemCompSubcomponentStructConn property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxChemCompSubcomponentStructConn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxChemCompSubcomponentStructConnType.PdbxChemCompSubcomponentStructConn }
     * 
     * 
     */
    public List<PdbxChemCompSubcomponentStructConnType.PdbxChemCompSubcomponentStructConn> getPdbxChemCompSubcomponentStructConn() {
        if (pdbxChemCompSubcomponentStructConn == null) {
            pdbxChemCompSubcomponentStructConn = new ArrayList<PdbxChemCompSubcomponentStructConnType.PdbxChemCompSubcomponentStructConn>();
        }
        return this.pdbxChemCompSubcomponentStructConn;
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
     *         &lt;element name="atom_id_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="atom_id_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="comp_id_1" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="comp_id_2" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="entity_id_1" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="entity_id_2" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="seq_id_1" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="seq_id_2" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="type">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="covale"/>
     *               &lt;enumeration value="disulf"/>
     *               &lt;enumeration value="hydrog"/>
     *               &lt;enumeration value="metalc"/>
     *               &lt;enumeration value="mismat"/>
     *               &lt;enumeration value="saltbr"/>
     *               &lt;enumeration value="covale_base"/>
     *               &lt;enumeration value="covale_sugar"/>
     *               &lt;enumeration value="covale_phosphate"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/all>
     *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
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
    public static class PdbxChemCompSubcomponentStructConn {

        @XmlElement(name = "atom_id_1", required = true)
        protected String atomId1;
        @XmlElement(name = "atom_id_2", required = true)
        protected String atomId2;
        @XmlElement(name = "comp_id_1", required = true)
        protected String compId1;
        @XmlElement(name = "comp_id_2", required = true)
        protected String compId2;
        @XmlElement(name = "entity_id_1", required = true)
        protected BigInteger entityId1;
        @XmlElement(name = "entity_id_2", required = true)
        protected BigInteger entityId2;
        @XmlElement(name = "seq_id_1", required = true)
        protected BigInteger seqId1;
        @XmlElement(name = "seq_id_2", required = true)
        protected BigInteger seqId2;
        @XmlElement(required = true)
        protected String type;
        @XmlAttribute(name = "id", required = true)
        protected BigInteger id;

        /**
         * Gets the value of the atomId1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAtomId1() {
            return atomId1;
        }

        /**
         * Sets the value of the atomId1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAtomId1(String value) {
            this.atomId1 = value;
        }

        /**
         * Gets the value of the atomId2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAtomId2() {
            return atomId2;
        }

        /**
         * Sets the value of the atomId2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAtomId2(String value) {
            this.atomId2 = value;
        }

        /**
         * Gets the value of the compId1 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCompId1() {
            return compId1;
        }

        /**
         * Sets the value of the compId1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCompId1(String value) {
            this.compId1 = value;
        }

        /**
         * Gets the value of the compId2 property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCompId2() {
            return compId2;
        }

        /**
         * Sets the value of the compId2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCompId2(String value) {
            this.compId2 = value;
        }

        /**
         * Gets the value of the entityId1 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getEntityId1() {
            return entityId1;
        }

        /**
         * Sets the value of the entityId1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setEntityId1(BigInteger value) {
            this.entityId1 = value;
        }

        /**
         * Gets the value of the entityId2 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getEntityId2() {
            return entityId2;
        }

        /**
         * Sets the value of the entityId2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setEntityId2(BigInteger value) {
            this.entityId2 = value;
        }

        /**
         * Gets the value of the seqId1 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getSeqId1() {
            return seqId1;
        }

        /**
         * Sets the value of the seqId1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSeqId1(BigInteger value) {
            this.seqId1 = value;
        }

        /**
         * Gets the value of the seqId2 property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getSeqId2() {
            return seqId2;
        }

        /**
         * Sets the value of the seqId2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setSeqId2(BigInteger value) {
            this.seqId2 = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setId(BigInteger value) {
            this.id = value;
        }

    }

}
