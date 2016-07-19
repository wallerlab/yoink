//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.06 at 06:37:17 PM CEST 
//


package org.pdbx_v42_v4.generated;

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
 * Data items in the PDBX_FEATURE_ASSEMBLY category records 
 * information about properties pertaining to this 
 * structural assembly.
 * 
 *     Example 1 - Biological process for functional assembly  
 * <PDBx:pdbx_feature_assemblyCategory>
 *    <PDBx:pdbx_feature_assembly id="1">
 *       <PDBx:assembly_id>b1</PDBx:assembly_id>
 *       <PDBx:feature>nitrogen metabolism</PDBx:feature>
 *       <PDBx:feature_assigned_by>GO</PDBx:feature_assigned_by>
 *       <PDBx:feature_citation_id>GO</PDBx:feature_citation_id>
 *       <PDBx:feature_name>biological process</PDBx:feature_name>
 *       <PDBx:feature_type>value</PDBx:feature_type>
 *    </PDBx:pdbx_feature_assembly>
 * </PDBx:pdbx_feature_assemblyCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_feature_assemblyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_feature_assemblyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_feature_assembly" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="assembly_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="feature" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="feature_assigned_by" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="feature_citation_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="feature_identifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="feature_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="feature_software_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="feature_type">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="value"/>
 *                         &lt;enumeration value="uri"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "pdbx_feature_assemblyType", propOrder = {
    "pdbxFeatureAssembly"
})
public class PdbxFeatureAssemblyType {

    @XmlElement(name = "pdbx_feature_assembly")
    protected List<PdbxFeatureAssemblyType.PdbxFeatureAssembly> pdbxFeatureAssembly;

    /**
     * Gets the value of the pdbxFeatureAssembly property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxFeatureAssembly property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxFeatureAssembly().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxFeatureAssemblyType.PdbxFeatureAssembly }
     * 
     * 
     */
    public List<PdbxFeatureAssemblyType.PdbxFeatureAssembly> getPdbxFeatureAssembly() {
        if (pdbxFeatureAssembly == null) {
            pdbxFeatureAssembly = new ArrayList<PdbxFeatureAssemblyType.PdbxFeatureAssembly>();
        }
        return this.pdbxFeatureAssembly;
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
     *         &lt;element name="assembly_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="feature" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="feature_assigned_by" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="feature_citation_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="feature_identifier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="feature_name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="feature_software_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="feature_type">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="value"/>
     *               &lt;enumeration value="uri"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/all>
     *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class PdbxFeatureAssembly {

        @XmlElement(name = "assembly_id", required = true)
        protected String assemblyId;
        @XmlElement(required = true)
        protected String feature;
        @XmlElement(name = "feature_assigned_by", required = true)
        protected String featureAssignedBy;
        @XmlElementRef(name = "feature_citation_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> featureCitationId;
        @XmlElementRef(name = "feature_identifier", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> featureIdentifier;
        @XmlElement(name = "feature_name", required = true)
        protected String featureName;
        @XmlElementRef(name = "feature_software_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> featureSoftwareId;
        @XmlElement(name = "feature_type", required = true)
        protected String featureType;
        @XmlAttribute(name = "id", required = true)
        protected String id;

        /**
         * Gets the value of the assemblyId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAssemblyId() {
            return assemblyId;
        }

        /**
         * Sets the value of the assemblyId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAssemblyId(String value) {
            this.assemblyId = value;
        }

        /**
         * Gets the value of the feature property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFeature() {
            return feature;
        }

        /**
         * Sets the value of the feature property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFeature(String value) {
            this.feature = value;
        }

        /**
         * Gets the value of the featureAssignedBy property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFeatureAssignedBy() {
            return featureAssignedBy;
        }

        /**
         * Sets the value of the featureAssignedBy property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFeatureAssignedBy(String value) {
            this.featureAssignedBy = value;
        }

        /**
         * Gets the value of the featureCitationId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getFeatureCitationId() {
            return featureCitationId;
        }

        /**
         * Sets the value of the featureCitationId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setFeatureCitationId(JAXBElement<String> value) {
            this.featureCitationId = value;
        }

        /**
         * Gets the value of the featureIdentifier property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getFeatureIdentifier() {
            return featureIdentifier;
        }

        /**
         * Sets the value of the featureIdentifier property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setFeatureIdentifier(JAXBElement<String> value) {
            this.featureIdentifier = value;
        }

        /**
         * Gets the value of the featureName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFeatureName() {
            return featureName;
        }

        /**
         * Sets the value of the featureName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFeatureName(String value) {
            this.featureName = value;
        }

        /**
         * Gets the value of the featureSoftwareId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getFeatureSoftwareId() {
            return featureSoftwareId;
        }

        /**
         * Sets the value of the featureSoftwareId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setFeatureSoftwareId(JAXBElement<String> value) {
            this.featureSoftwareId = value;
        }

        /**
         * Gets the value of the featureType property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getFeatureType() {
            return featureType;
        }

        /**
         * Sets the value of the featureType property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setFeatureType(String value) {
            this.featureType = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }

    }

}
