//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.22 at 05:23:04 PM EST 
//


package org.gradle.pdbml.v40.generated;

import java.math.BigDecimal;
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
 * 
 * Items in the assigned_chem_shift_list category provide information about a list of reported assigned chemical shift values.
 * 
 *          
 * 
 * <p>Java class for pdbx_nmr_assigned_chem_shift_listType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_nmr_assigned_chem_shift_listType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_nmr_assigned_chem_shift_list" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="chem_shift_13C_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="chem_shift_15N_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="chem_shift_19F_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="chem_shift_1H_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="chem_shift_2H_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="chem_shift_31P_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="chem_shift_reference_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="conditions_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                   &lt;element name="conditions_label" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="data_file_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="error_derivation_method" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="entry_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "pdbx_nmr_assigned_chem_shift_listType", propOrder = {
    "pdbxNmrAssignedChemShiftList"
})
public class PdbxNmrAssignedChemShiftListType {

    @XmlElement(name = "pdbx_nmr_assigned_chem_shift_list")
    protected List<PdbxNmrAssignedChemShiftListType.PdbxNmrAssignedChemShiftList> pdbxNmrAssignedChemShiftList;

    /**
     * Gets the value of the pdbxNmrAssignedChemShiftList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxNmrAssignedChemShiftList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxNmrAssignedChemShiftList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxNmrAssignedChemShiftListType.PdbxNmrAssignedChemShiftList }
     * 
     * 
     */
    public List<PdbxNmrAssignedChemShiftListType.PdbxNmrAssignedChemShiftList> getPdbxNmrAssignedChemShiftList() {
        if (pdbxNmrAssignedChemShiftList == null) {
            pdbxNmrAssignedChemShiftList = new ArrayList<PdbxNmrAssignedChemShiftListType.PdbxNmrAssignedChemShiftList>();
        }
        return this.pdbxNmrAssignedChemShiftList;
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
     *         &lt;element name="chem_shift_13C_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="chem_shift_15N_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="chem_shift_19F_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="chem_shift_1H_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="chem_shift_2H_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="chem_shift_31P_err" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="chem_shift_reference_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="conditions_id" type="{http://www.w3.org/2001/XMLSchema}integer"/>
     *         &lt;element name="conditions_label" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="data_file_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="error_derivation_method" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="label" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="entry_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class PdbxNmrAssignedChemShiftList {

        @XmlElementRef(name = "chem_shift_13C_err", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> chemShift13CErr;
        @XmlElementRef(name = "chem_shift_15N_err", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> chemShift15NErr;
        @XmlElementRef(name = "chem_shift_19F_err", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> chemShift19FErr;
        @XmlElementRef(name = "chem_shift_1H_err", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> chemShift1HErr;
        @XmlElementRef(name = "chem_shift_2H_err", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> chemShift2HErr;
        @XmlElementRef(name = "chem_shift_31P_err", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> chemShift31PErr;
        @XmlElement(name = "chem_shift_reference_id", required = true)
        protected BigInteger chemShiftReferenceId;
        @XmlElement(name = "conditions_id", required = true)
        protected BigInteger conditionsId;
        @XmlElementRef(name = "conditions_label", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> conditionsLabel;
        @XmlElement(name = "data_file_name", required = true)
        protected String dataFileName;
        @XmlElementRef(name = "details", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> details;
        @XmlElementRef(name = "error_derivation_method", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> errorDerivationMethod;
        @XmlElementRef(name = "label", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> label;
        @XmlAttribute(name = "entry_id", required = true)
        protected String entryId;
        @XmlAttribute(name = "id", required = true)
        protected BigInteger id;

        /**
         * Gets the value of the chemShift13CErr property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getChemShift13CErr() {
            return chemShift13CErr;
        }

        /**
         * Sets the value of the chemShift13CErr property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setChemShift13CErr(JAXBElement<BigDecimal> value) {
            this.chemShift13CErr = value;
        }

        /**
         * Gets the value of the chemShift15NErr property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getChemShift15NErr() {
            return chemShift15NErr;
        }

        /**
         * Sets the value of the chemShift15NErr property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setChemShift15NErr(JAXBElement<BigDecimal> value) {
            this.chemShift15NErr = value;
        }

        /**
         * Gets the value of the chemShift19FErr property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getChemShift19FErr() {
            return chemShift19FErr;
        }

        /**
         * Sets the value of the chemShift19FErr property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setChemShift19FErr(JAXBElement<BigDecimal> value) {
            this.chemShift19FErr = value;
        }

        /**
         * Gets the value of the chemShift1HErr property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getChemShift1HErr() {
            return chemShift1HErr;
        }

        /**
         * Sets the value of the chemShift1HErr property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setChemShift1HErr(JAXBElement<BigDecimal> value) {
            this.chemShift1HErr = value;
        }

        /**
         * Gets the value of the chemShift2HErr property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getChemShift2HErr() {
            return chemShift2HErr;
        }

        /**
         * Sets the value of the chemShift2HErr property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setChemShift2HErr(JAXBElement<BigDecimal> value) {
            this.chemShift2HErr = value;
        }

        /**
         * Gets the value of the chemShift31PErr property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getChemShift31PErr() {
            return chemShift31PErr;
        }

        /**
         * Sets the value of the chemShift31PErr property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setChemShift31PErr(JAXBElement<BigDecimal> value) {
            this.chemShift31PErr = value;
        }

        /**
         * Gets the value of the chemShiftReferenceId property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getChemShiftReferenceId() {
            return chemShiftReferenceId;
        }

        /**
         * Sets the value of the chemShiftReferenceId property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setChemShiftReferenceId(BigInteger value) {
            this.chemShiftReferenceId = value;
        }

        /**
         * Gets the value of the conditionsId property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getConditionsId() {
            return conditionsId;
        }

        /**
         * Sets the value of the conditionsId property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setConditionsId(BigInteger value) {
            this.conditionsId = value;
        }

        /**
         * Gets the value of the conditionsLabel property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getConditionsLabel() {
            return conditionsLabel;
        }

        /**
         * Sets the value of the conditionsLabel property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setConditionsLabel(JAXBElement<String> value) {
            this.conditionsLabel = value;
        }

        /**
         * Gets the value of the dataFileName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDataFileName() {
            return dataFileName;
        }

        /**
         * Sets the value of the dataFileName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDataFileName(String value) {
            this.dataFileName = value;
        }

        /**
         * Gets the value of the details property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getDetails() {
            return details;
        }

        /**
         * Sets the value of the details property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setDetails(JAXBElement<String> value) {
            this.details = value;
        }

        /**
         * Gets the value of the errorDerivationMethod property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getErrorDerivationMethod() {
            return errorDerivationMethod;
        }

        /**
         * Sets the value of the errorDerivationMethod property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setErrorDerivationMethod(JAXBElement<String> value) {
            this.errorDerivationMethod = value;
        }

        /**
         * Gets the value of the label property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getLabel() {
            return label;
        }

        /**
         * Sets the value of the label property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setLabel(JAXBElement<String> value) {
            this.label = value;
        }

        /**
         * Gets the value of the entryId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEntryId() {
            return entryId;
        }

        /**
         * Sets the value of the entryId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEntryId(String value) {
            this.entryId = value;
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
