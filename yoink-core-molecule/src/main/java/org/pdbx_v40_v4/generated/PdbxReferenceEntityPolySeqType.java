//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.22 at 05:23:04 PM EST 
//


package org.gradle.pdbml.v40.generated;

import java.math.BigInteger;
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
 * Data items in the PDBX_REFERENCE_ENTITY_POLY_SEQ category specify the sequence
 * of monomers in a polymer. 
 * 
 *     Example: 1  Actinomycin
 *                 
 * <PDBx:pdbx_reference_entity_poly_seqCategory>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="THR" num="1" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>THR</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="DVA" num="2" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>VAL</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="PRO" num="3" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>PRO</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="SAR" num="4" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>GLY</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="MVA" num="5" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>VAL</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="PXZ" num="6" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id xsi:nil="true" />
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="THR" num="7" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>THR</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="DVA" num="8" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>VAL</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="PRO" num="9" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>PRO</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="SAR" num="10" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>GLY</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 *    <PDBx:pdbx_reference_entity_poly_seq hetero="N" mon_id="MVA" num="11" prd_id="PRD_000001" ref_entity_id="1">
 *       <PDBx:observed>Y</PDBx:observed>
 *       <PDBx:parent_mon_id>VAL</PDBx:parent_mon_id>
 *    </PDBx:pdbx_reference_entity_poly_seq>
 * </PDBx:pdbx_reference_entity_poly_seqCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_reference_entity_poly_seqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_reference_entity_poly_seqType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_reference_entity_poly_seq" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="observed" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Y"/>
 *                         &lt;enumeration value="N"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="parent_mon_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="hetero" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="Y"/>
 *                       &lt;enumeration value="N"/>
 *                     &lt;/restriction>
 *                   &lt;/simpleType>
 *                 &lt;/attribute>
 *                 &lt;attribute name="mon_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="prd_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ref_entity_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "pdbx_reference_entity_poly_seqType", propOrder = {
    "pdbxReferenceEntityPolySeq"
})
public class PdbxReferenceEntityPolySeqType {

    @XmlElement(name = "pdbx_reference_entity_poly_seq")
    protected List<PdbxReferenceEntityPolySeqType.PdbxReferenceEntityPolySeq> pdbxReferenceEntityPolySeq;

    /**
     * Gets the value of the pdbxReferenceEntityPolySeq property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxReferenceEntityPolySeq property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxReferenceEntityPolySeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxReferenceEntityPolySeqType.PdbxReferenceEntityPolySeq }
     * 
     * 
     */
    public List<PdbxReferenceEntityPolySeqType.PdbxReferenceEntityPolySeq> getPdbxReferenceEntityPolySeq() {
        if (pdbxReferenceEntityPolySeq == null) {
            pdbxReferenceEntityPolySeq = new ArrayList<PdbxReferenceEntityPolySeqType.PdbxReferenceEntityPolySeq>();
        }
        return this.pdbxReferenceEntityPolySeq;
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
     *         &lt;element name="observed" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Y"/>
     *               &lt;enumeration value="N"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="parent_mon_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="hetero" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="Y"/>
     *             &lt;enumeration value="N"/>
     *           &lt;/restriction>
     *         &lt;/simpleType>
     *       &lt;/attribute>
     *       &lt;attribute name="mon_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="num" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="prd_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ref_entity_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class PdbxReferenceEntityPolySeq {

        @XmlElementRef(name = "observed", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> observed;
        @XmlElementRef(name = "parent_mon_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> parentMonId;
        @XmlAttribute(name = "hetero", required = true)
        protected String hetero;
        @XmlAttribute(name = "mon_id", required = true)
        protected String monId;
        @XmlAttribute(name = "num", required = true)
        protected BigInteger num;
        @XmlAttribute(name = "prd_id", required = true)
        protected String prdId;
        @XmlAttribute(name = "ref_entity_id", required = true)
        protected String refEntityId;

        /**
         * Gets the value of the observed property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getObserved() {
            return observed;
        }

        /**
         * Sets the value of the observed property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setObserved(JAXBElement<String> value) {
            this.observed = value;
        }

        /**
         * Gets the value of the parentMonId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getParentMonId() {
            return parentMonId;
        }

        /**
         * Sets the value of the parentMonId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setParentMonId(JAXBElement<String> value) {
            this.parentMonId = value;
        }

        /**
         * Gets the value of the hetero property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHetero() {
            return hetero;
        }

        /**
         * Sets the value of the hetero property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHetero(String value) {
            this.hetero = value;
        }

        /**
         * Gets the value of the monId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMonId() {
            return monId;
        }

        /**
         * Sets the value of the monId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMonId(String value) {
            this.monId = value;
        }

        /**
         * Gets the value of the num property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getNum() {
            return num;
        }

        /**
         * Sets the value of the num property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setNum(BigInteger value) {
            this.num = value;
        }

        /**
         * Gets the value of the prdId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPrdId() {
            return prdId;
        }

        /**
         * Sets the value of the prdId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPrdId(String value) {
            this.prdId = value;
        }

        /**
         * Gets the value of the refEntityId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRefEntityId() {
            return refEntityId;
        }

        /**
         * Sets the value of the refEntityId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRefEntityId(String value) {
            this.refEntityId = value;
        }

    }

}
