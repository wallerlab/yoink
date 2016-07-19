//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.06 at 06:37:17 PM CEST 
//


package org.pdbx_v42_v4.generated;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Data items in the EXPTL category record details about the
 * experimental work prior to the intensity measurements and
 * details about the absorption-correction technique employed.
 * 
 *     Example 1 - based on laboratory records for Yb(S-C5H4N)2(THF)4.
 * <PDBx:exptlCategory>
 *    <PDBx:exptl entry_id="datablock1" method="single-crystal x-ray diffraction">
 *       <PDBx:absorpt_coefficient_mu>1.22</PDBx:absorpt_coefficient_mu>
 *       <PDBx:absorpt_correction_T_max>0.896</PDBx:absorpt_correction_T_max>
 *       <PDBx:absorpt_correction_T_min>0.802</PDBx:absorpt_correction_T_min>
 *       <PDBx:absorpt_correction_type>integration</PDBx:absorpt_correction_type>
 *       <PDBx:absorpt_process_details> Gaussian grid method from SHELX76
 * Sheldrick, G. M., &quot;SHELX-76: structure determination and
 * refinement program&quot;, Cambridge University, UK, 1976</PDBx:absorpt_process_details>
 *       <PDBx:crystals_number>1</PDBx:crystals_number>
 *       <PDBx:details> Enraf-Nonius LT2 liquid nitrogen variable-temperature
 * device used</PDBx:details>
 *       <PDBx:method_details> graphite monochromatized Cu K(alpha) fixed tube and
 * Enraf-Nonius CAD4 diffractometer used</PDBx:method_details>
 *    </PDBx:exptl>
 * </PDBx:exptlCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for exptlType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exptlType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="exptl" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="absorpt_coefficient_mu" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0.0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="absorpt_correction_T_max" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0.0"/>
 *                         &lt;maxInclusive value="1.0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="absorpt_correction_T_min" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0.0"/>
 *                         &lt;maxInclusive value="1.0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="absorpt_correction_type" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="analytical"/>
 *                         &lt;enumeration value="cylinder"/>
 *                         &lt;enumeration value="empirical"/>
 *                         &lt;enumeration value="gaussian"/>
 *                         &lt;enumeration value="integration"/>
 *                         &lt;enumeration value="multi-scan"/>
 *                         &lt;enumeration value="none"/>
 *                         &lt;enumeration value="numerical"/>
 *                         &lt;enumeration value="psi-scan"/>
 *                         &lt;enumeration value="refdelf"/>
 *                         &lt;enumeration value="sphere"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="absorpt_process_details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="crystals_number" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="method_details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="entry_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="method" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                       &lt;enumeration value="X-RAY DIFFRACTION"/>
 *                       &lt;enumeration value="NEUTRON DIFFRACTION"/>
 *                       &lt;enumeration value="FIBER DIFFRACTION"/>
 *                       &lt;enumeration value="ELECTRON CRYSTALLOGRAPHY"/>
 *                       &lt;enumeration value="ELECTRON MICROSCOPY"/>
 *                       &lt;enumeration value="SOLUTION NMR"/>
 *                       &lt;enumeration value="SOLID-STATE NMR"/>
 *                       &lt;enumeration value="SOLUTION SCATTERING"/>
 *                       &lt;enumeration value="POWDER DIFFRACTION"/>
 *                       &lt;enumeration value="INFRARED SPECTROSCOPY"/>
 *                       &lt;enumeration value="EPR"/>
 *                       &lt;enumeration value="FLUORESCENCE TRANSFER"/>
 *                       &lt;enumeration value="THEORETICAL MODEL"/>
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
@XmlType(name = "exptlType", propOrder = {
    "exptl"
})
public class ExptlType {

    protected List<ExptlType.Exptl> exptl;

    /**
     * Gets the value of the exptl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exptl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExptl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExptlType.Exptl }
     * 
     * 
     */
    public List<ExptlType.Exptl> getExptl() {
        if (exptl == null) {
            exptl = new ArrayList<ExptlType.Exptl>();
        }
        return this.exptl;
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
     *         &lt;element name="absorpt_coefficient_mu" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0.0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="absorpt_correction_T_max" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0.0"/>
     *               &lt;maxInclusive value="1.0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="absorpt_correction_T_min" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0.0"/>
     *               &lt;maxInclusive value="1.0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="absorpt_correction_type" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="analytical"/>
     *               &lt;enumeration value="cylinder"/>
     *               &lt;enumeration value="empirical"/>
     *               &lt;enumeration value="gaussian"/>
     *               &lt;enumeration value="integration"/>
     *               &lt;enumeration value="multi-scan"/>
     *               &lt;enumeration value="none"/>
     *               &lt;enumeration value="numerical"/>
     *               &lt;enumeration value="psi-scan"/>
     *               &lt;enumeration value="refdelf"/>
     *               &lt;enumeration value="sphere"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="absorpt_process_details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="crystals_number" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="method_details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="entry_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="method" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *             &lt;enumeration value="X-RAY DIFFRACTION"/>
     *             &lt;enumeration value="NEUTRON DIFFRACTION"/>
     *             &lt;enumeration value="FIBER DIFFRACTION"/>
     *             &lt;enumeration value="ELECTRON CRYSTALLOGRAPHY"/>
     *             &lt;enumeration value="ELECTRON MICROSCOPY"/>
     *             &lt;enumeration value="SOLUTION NMR"/>
     *             &lt;enumeration value="SOLID-STATE NMR"/>
     *             &lt;enumeration value="SOLUTION SCATTERING"/>
     *             &lt;enumeration value="POWDER DIFFRACTION"/>
     *             &lt;enumeration value="INFRARED SPECTROSCOPY"/>
     *             &lt;enumeration value="EPR"/>
     *             &lt;enumeration value="FLUORESCENCE TRANSFER"/>
     *             &lt;enumeration value="THEORETICAL MODEL"/>
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
    @XmlType(name = "", propOrder = {

    })
    public static class Exptl {

        @XmlElementRef(name = "absorpt_coefficient_mu", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> absorptCoefficientMu;
        @XmlElementRef(name = "absorpt_correction_T_max", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> absorptCorrectionTMax;
        @XmlElementRef(name = "absorpt_correction_T_min", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> absorptCorrectionTMin;
        @XmlElementRef(name = "absorpt_correction_type", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> absorptCorrectionType;
        @XmlElementRef(name = "absorpt_process_details", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> absorptProcessDetails;
        @XmlElementRef(name = "crystals_number", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigInteger> crystalsNumber;
        @XmlElementRef(name = "details", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> details;
        @XmlElementRef(name = "method_details", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> methodDetails;
        @XmlAttribute(name = "entry_id", required = true)
        protected String entryId;
        @XmlAttribute(name = "method", required = true)
        protected String method;

        /**
         * Gets the value of the absorptCoefficientMu property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getAbsorptCoefficientMu() {
            return absorptCoefficientMu;
        }

        /**
         * Sets the value of the absorptCoefficientMu property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setAbsorptCoefficientMu(JAXBElement<BigDecimal> value) {
            this.absorptCoefficientMu = value;
        }

        /**
         * Gets the value of the absorptCorrectionTMax property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getAbsorptCorrectionTMax() {
            return absorptCorrectionTMax;
        }

        /**
         * Sets the value of the absorptCorrectionTMax property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setAbsorptCorrectionTMax(JAXBElement<BigDecimal> value) {
            this.absorptCorrectionTMax = value;
        }

        /**
         * Gets the value of the absorptCorrectionTMin property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getAbsorptCorrectionTMin() {
            return absorptCorrectionTMin;
        }

        /**
         * Sets the value of the absorptCorrectionTMin property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setAbsorptCorrectionTMin(JAXBElement<BigDecimal> value) {
            this.absorptCorrectionTMin = value;
        }

        /**
         * Gets the value of the absorptCorrectionType property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getAbsorptCorrectionType() {
            return absorptCorrectionType;
        }

        /**
         * Sets the value of the absorptCorrectionType property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setAbsorptCorrectionType(JAXBElement<String> value) {
            this.absorptCorrectionType = value;
        }

        /**
         * Gets the value of the absorptProcessDetails property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getAbsorptProcessDetails() {
            return absorptProcessDetails;
        }

        /**
         * Sets the value of the absorptProcessDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setAbsorptProcessDetails(JAXBElement<String> value) {
            this.absorptProcessDetails = value;
        }

        /**
         * Gets the value of the crystalsNumber property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public JAXBElement<BigInteger> getCrystalsNumber() {
            return crystalsNumber;
        }

        /**
         * Sets the value of the crystalsNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public void setCrystalsNumber(JAXBElement<BigInteger> value) {
            this.crystalsNumber = value;
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
         * Gets the value of the methodDetails property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getMethodDetails() {
            return methodDetails;
        }

        /**
         * Sets the value of the methodDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setMethodDetails(JAXBElement<String> value) {
            this.methodDetails = value;
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
         * Gets the value of the method property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMethod() {
            return method;
        }

        /**
         * Sets the value of the method property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMethod(String value) {
            this.method = value;
        }

    }

}
