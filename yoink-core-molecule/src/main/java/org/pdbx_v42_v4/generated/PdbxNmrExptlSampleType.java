//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.06 at 06:37:17 PM CEST 
//


package org.pdbx_v42_v4.generated;

import java.math.BigDecimal;
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
 * The chemical constituents of
 * each NMR sample. Each sample is identified by a number and 
 * each component in the sample is identified by name.   
 *  
 *   Example 1 
 * 
 *   This example was taken from the study of MCP-1 which is a dimer under the 
 *   conditions studied. Three solutions with different isotope compositions were 
 *   studied.
 * <PDBx:pdbx_nmr_exptl_sampleCategory>
 *    <PDBx:pdbx_nmr_exptl_sample component="MCP-1" solution_id="1">
 *       <PDBx:concentration>2</PDBx:concentration>
 *       <PDBx:concentration_units>mM</PDBx:concentration_units>
 *       <PDBx:isotopic_labeling>U-15N,13C</PDBx:isotopic_labeling>
 *    </PDBx:pdbx_nmr_exptl_sample>
 *    <PDBx:pdbx_nmr_exptl_sample component="H2O" solution_id="1">
 *       <PDBx:concentration>90</PDBx:concentration>
 *       <PDBx:concentration_units>&#37;</PDBx:concentration_units>
 *       <PDBx:isotopic_labeling xsi:nil="true" />
 *    </PDBx:pdbx_nmr_exptl_sample>
 *    <PDBx:pdbx_nmr_exptl_sample component="D2O" solution_id="1">
 *       <PDBx:concentration>10</PDBx:concentration>
 *       <PDBx:concentration_units>&#37;</PDBx:concentration_units>
 *       <PDBx:isotopic_labeling xsi:nil="true" />
 *    </PDBx:pdbx_nmr_exptl_sample>
 * </PDBx:pdbx_nmr_exptl_sampleCategory>
 * 
 *  
 *   Example 2
 * 
 *   This example was taken from the study of MCP-1 which is a dimer under the 
 *   conditions studied. Three solutions with different isotope compositions were 
 *   studied.
 * <PDBx:pdbx_nmr_exptl_sampleCategory>
 *    <PDBx:pdbx_nmr_exptl_sample component="MCP-1" solution_id="2">
 *       <PDBx:concentration>1</PDBx:concentration>
 *       <PDBx:concentration_units>mM</PDBx:concentration_units>
 *       <PDBx:isotopic_labeling>U-50&#37; 15N</PDBx:isotopic_labeling>
 *    </PDBx:pdbx_nmr_exptl_sample>
 *    <PDBx:pdbx_nmr_exptl_sample component="H2O" solution_id="2">
 *       <PDBx:concentration>90</PDBx:concentration>
 *       <PDBx:concentration_units>&#37;</PDBx:concentration_units>
 *       <PDBx:isotopic_labeling xsi:nil="true" />
 *    </PDBx:pdbx_nmr_exptl_sample>
 *    <PDBx:pdbx_nmr_exptl_sample component="D2O" solution_id="2">
 *       <PDBx:concentration>10</PDBx:concentration>
 *       <PDBx:concentration_units>&#37;</PDBx:concentration_units>
 *       <PDBx:isotopic_labeling xsi:nil="true" />
 *    </PDBx:pdbx_nmr_exptl_sample>
 * </PDBx:pdbx_nmr_exptl_sampleCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_nmr_exptl_sampleType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_nmr_exptl_sampleType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_nmr_exptl_sample" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="concentration" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="concentration_range" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="concentration_units" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="%"/>
 *                         &lt;enumeration value="mM"/>
 *                         &lt;enumeration value="mg/mL"/>
 *                         &lt;enumeration value="M"/>
 *                         &lt;enumeration value="g/L"/>
 *                         &lt;enumeration value="uM"/>
 *                         &lt;enumeration value="v/v"/>
 *                         &lt;enumeration value="w/v"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="isotopic_labeling" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="component" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="solution_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "pdbx_nmr_exptl_sampleType", propOrder = {
    "pdbxNmrExptlSample"
})
public class PdbxNmrExptlSampleType {

    @XmlElement(name = "pdbx_nmr_exptl_sample")
    protected List<PdbxNmrExptlSampleType.PdbxNmrExptlSample> pdbxNmrExptlSample;

    /**
     * Gets the value of the pdbxNmrExptlSample property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxNmrExptlSample property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxNmrExptlSample().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxNmrExptlSampleType.PdbxNmrExptlSample }
     * 
     * 
     */
    public List<PdbxNmrExptlSampleType.PdbxNmrExptlSample> getPdbxNmrExptlSample() {
        if (pdbxNmrExptlSample == null) {
            pdbxNmrExptlSample = new ArrayList<PdbxNmrExptlSampleType.PdbxNmrExptlSample>();
        }
        return this.pdbxNmrExptlSample;
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
     *         &lt;element name="concentration" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="concentration_range" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="concentration_units" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="%"/>
     *               &lt;enumeration value="mM"/>
     *               &lt;enumeration value="mg/mL"/>
     *               &lt;enumeration value="M"/>
     *               &lt;enumeration value="g/L"/>
     *               &lt;enumeration value="uM"/>
     *               &lt;enumeration value="v/v"/>
     *               &lt;enumeration value="w/v"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="isotopic_labeling" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="component" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="solution_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class PdbxNmrExptlSample {

        @XmlElementRef(name = "concentration", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> concentration;
        @XmlElementRef(name = "concentration_range", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> concentrationRange;
        @XmlElementRef(name = "concentration_units", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> concentrationUnits;
        @XmlElementRef(name = "isotopic_labeling", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> isotopicLabeling;
        @XmlAttribute(name = "component", required = true)
        protected String component;
        @XmlAttribute(name = "solution_id", required = true)
        protected String solutionId;

        /**
         * Gets the value of the concentration property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getConcentration() {
            return concentration;
        }

        /**
         * Sets the value of the concentration property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setConcentration(JAXBElement<BigDecimal> value) {
            this.concentration = value;
        }

        /**
         * Gets the value of the concentrationRange property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getConcentrationRange() {
            return concentrationRange;
        }

        /**
         * Sets the value of the concentrationRange property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setConcentrationRange(JAXBElement<String> value) {
            this.concentrationRange = value;
        }

        /**
         * Gets the value of the concentrationUnits property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getConcentrationUnits() {
            return concentrationUnits;
        }

        /**
         * Sets the value of the concentrationUnits property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setConcentrationUnits(JAXBElement<String> value) {
            this.concentrationUnits = value;
        }

        /**
         * Gets the value of the isotopicLabeling property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getIsotopicLabeling() {
            return isotopicLabeling;
        }

        /**
         * Sets the value of the isotopicLabeling property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setIsotopicLabeling(JAXBElement<String> value) {
            this.isotopicLabeling = value;
        }

        /**
         * Gets the value of the component property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComponent() {
            return component;
        }

        /**
         * Sets the value of the component property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComponent(String value) {
            this.component = value;
        }

        /**
         * Gets the value of the solutionId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSolutionId() {
            return solutionId;
        }

        /**
         * Sets the value of the solutionId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSolutionId(String value) {
            this.solutionId = value;
        }

    }

}
