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
 * Data items in the PDBX_MOLECULE_FEATURES category record features of molecules
 * within a PDB entry.
 * 
 *     Example 1 - 
 * <PDBx:pdbx_molecule_featuresCategory>
 *    <PDBx:pdbx_molecule_features prd_id="PRD_000001">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:name>Actinomycin D</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_molecule_features>
 * </PDBx:pdbx_molecule_featuresCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_molecule_featuresType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_molecule_featuresType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_molecule_features" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="class" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Antagonist"/>
 *                         &lt;enumeration value="Antibiotic"/>
 *                         &lt;enumeration value="Anticancer"/>
 *                         &lt;enumeration value="Anticoagulant"/>
 *                         &lt;enumeration value="Antifungal"/>
 *                         &lt;enumeration value="Antiinflammatory"/>
 *                         &lt;enumeration value="Antimicrobial"/>
 *                         &lt;enumeration value="Antineoplastic"/>
 *                         &lt;enumeration value="Antiparasitic"/>
 *                         &lt;enumeration value="Antiretroviral"/>
 *                         &lt;enumeration value="Anthelmintic"/>
 *                         &lt;enumeration value="Antithrombotic"/>
 *                         &lt;enumeration value="Antitumor"/>
 *                         &lt;enumeration value="Antiviral"/>
 *                         &lt;enumeration value="CASPASE inhibitor"/>
 *                         &lt;enumeration value="Chaperone binding"/>
 *                         &lt;enumeration value="Enzyme inhibitor"/>
 *                         &lt;enumeration value="Growth factor"/>
 *                         &lt;enumeration value="Immunosuppressant"/>
 *                         &lt;enumeration value="Inhibitor"/>
 *                         &lt;enumeration value="Lantibiotic"/>
 *                         &lt;enumeration value="Metabolism"/>
 *                         &lt;enumeration value="Metal transport"/>
 *                         &lt;enumeration value="Oxidation-reduction"/>
 *                         &lt;enumeration value="Receptor"/>
 *                         &lt;enumeration value="Thrombin inhibitor"/>
 *                         &lt;enumeration value="Trypsin inhibitor"/>
 *                         &lt;enumeration value="Toxin"/>
 *                         &lt;enumeration value="Transport activator"/>
 *                         &lt;enumeration value="Unknown"/>
 *                         &lt;enumeration value="Anticoagulant, Antithrombotic"/>
 *                         &lt;enumeration value="Antibiotic, Antimicrobial"/>
 *                         &lt;enumeration value="Antibiotic, Anthelmintic"/>
 *                         &lt;enumeration value="Antibiotic, Antineoplastic"/>
 *                         &lt;enumeration value="Antimicrobial, Antiretroviral"/>
 *                         &lt;enumeration value="Antimicrobial, Antitumor"/>
 *                         &lt;enumeration value="Antimicrobial, Antiparasitic, Antibiotic"/>
 *                         &lt;enumeration value="Thrombin inhibitor, Trypsin inhibitor"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="type" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Amino acid"/>
 *                         &lt;enumeration value="Aminoglycoside"/>
 *                         &lt;enumeration value="Anthracycline"/>
 *                         &lt;enumeration value="Anthraquinone"/>
 *                         &lt;enumeration value="Ansamycin"/>
 *                         &lt;enumeration value="Chalkophore"/>
 *                         &lt;enumeration value="Chromophore"/>
 *                         &lt;enumeration value="Glycopeptide"/>
 *                         &lt;enumeration value="Cyclic depsipeptide"/>
 *                         &lt;enumeration value="Cyclic lipopeptide"/>
 *                         &lt;enumeration value="Cyclic peptide"/>
 *                         &lt;enumeration value="Heterocyclic"/>
 *                         &lt;enumeration value="Imino sugar"/>
 *                         &lt;enumeration value="Keto acid"/>
 *                         &lt;enumeration value="Lipoglycopeptide"/>
 *                         &lt;enumeration value="Lipopeptide"/>
 *                         &lt;enumeration value="Macrolide"/>
 *                         &lt;enumeration value="Non-polymer"/>
 *                         &lt;enumeration value="Nucleoside"/>
 *                         &lt;enumeration value="Oligopeptide"/>
 *                         &lt;enumeration value="Oligosaccharide"/>
 *                         &lt;enumeration value="Peptaibol"/>
 *                         &lt;enumeration value="Peptide-like"/>
 *                         &lt;enumeration value="Polycyclic"/>
 *                         &lt;enumeration value="Polypeptide"/>
 *                         &lt;enumeration value="Polysaccharide"/>
 *                         &lt;enumeration value="Quinolone"/>
 *                         &lt;enumeration value="Thiolactone"/>
 *                         &lt;enumeration value="Thiopeptide"/>
 *                         &lt;enumeration value="Siderophore"/>
 *                         &lt;enumeration value="Unknown"/>
 *                         &lt;enumeration value="Chalkophore, Polypeptide"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *                 &lt;attribute name="prd_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "pdbx_molecule_featuresType", propOrder = {
    "pdbxMoleculeFeatures"
})
public class PdbxMoleculeFeaturesType {

    @XmlElement(name = "pdbx_molecule_features")
    protected List<PdbxMoleculeFeaturesType.PdbxMoleculeFeatures> pdbxMoleculeFeatures;

    /**
     * Gets the value of the pdbxMoleculeFeatures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxMoleculeFeatures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxMoleculeFeatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxMoleculeFeaturesType.PdbxMoleculeFeatures }
     * 
     * 
     */
    public List<PdbxMoleculeFeaturesType.PdbxMoleculeFeatures> getPdbxMoleculeFeatures() {
        if (pdbxMoleculeFeatures == null) {
            pdbxMoleculeFeatures = new ArrayList<PdbxMoleculeFeaturesType.PdbxMoleculeFeatures>();
        }
        return this.pdbxMoleculeFeatures;
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
     *         &lt;element name="class" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Antagonist"/>
     *               &lt;enumeration value="Antibiotic"/>
     *               &lt;enumeration value="Anticancer"/>
     *               &lt;enumeration value="Anticoagulant"/>
     *               &lt;enumeration value="Antifungal"/>
     *               &lt;enumeration value="Antiinflammatory"/>
     *               &lt;enumeration value="Antimicrobial"/>
     *               &lt;enumeration value="Antineoplastic"/>
     *               &lt;enumeration value="Antiparasitic"/>
     *               &lt;enumeration value="Antiretroviral"/>
     *               &lt;enumeration value="Anthelmintic"/>
     *               &lt;enumeration value="Antithrombotic"/>
     *               &lt;enumeration value="Antitumor"/>
     *               &lt;enumeration value="Antiviral"/>
     *               &lt;enumeration value="CASPASE inhibitor"/>
     *               &lt;enumeration value="Chaperone binding"/>
     *               &lt;enumeration value="Enzyme inhibitor"/>
     *               &lt;enumeration value="Growth factor"/>
     *               &lt;enumeration value="Immunosuppressant"/>
     *               &lt;enumeration value="Inhibitor"/>
     *               &lt;enumeration value="Lantibiotic"/>
     *               &lt;enumeration value="Metabolism"/>
     *               &lt;enumeration value="Metal transport"/>
     *               &lt;enumeration value="Oxidation-reduction"/>
     *               &lt;enumeration value="Receptor"/>
     *               &lt;enumeration value="Thrombin inhibitor"/>
     *               &lt;enumeration value="Trypsin inhibitor"/>
     *               &lt;enumeration value="Toxin"/>
     *               &lt;enumeration value="Transport activator"/>
     *               &lt;enumeration value="Unknown"/>
     *               &lt;enumeration value="Anticoagulant, Antithrombotic"/>
     *               &lt;enumeration value="Antibiotic, Antimicrobial"/>
     *               &lt;enumeration value="Antibiotic, Anthelmintic"/>
     *               &lt;enumeration value="Antibiotic, Antineoplastic"/>
     *               &lt;enumeration value="Antimicrobial, Antiretroviral"/>
     *               &lt;enumeration value="Antimicrobial, Antitumor"/>
     *               &lt;enumeration value="Antimicrobial, Antiparasitic, Antibiotic"/>
     *               &lt;enumeration value="Thrombin inhibitor, Trypsin inhibitor"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="type" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Amino acid"/>
     *               &lt;enumeration value="Aminoglycoside"/>
     *               &lt;enumeration value="Anthracycline"/>
     *               &lt;enumeration value="Anthraquinone"/>
     *               &lt;enumeration value="Ansamycin"/>
     *               &lt;enumeration value="Chalkophore"/>
     *               &lt;enumeration value="Chromophore"/>
     *               &lt;enumeration value="Glycopeptide"/>
     *               &lt;enumeration value="Cyclic depsipeptide"/>
     *               &lt;enumeration value="Cyclic lipopeptide"/>
     *               &lt;enumeration value="Cyclic peptide"/>
     *               &lt;enumeration value="Heterocyclic"/>
     *               &lt;enumeration value="Imino sugar"/>
     *               &lt;enumeration value="Keto acid"/>
     *               &lt;enumeration value="Lipoglycopeptide"/>
     *               &lt;enumeration value="Lipopeptide"/>
     *               &lt;enumeration value="Macrolide"/>
     *               &lt;enumeration value="Non-polymer"/>
     *               &lt;enumeration value="Nucleoside"/>
     *               &lt;enumeration value="Oligopeptide"/>
     *               &lt;enumeration value="Oligosaccharide"/>
     *               &lt;enumeration value="Peptaibol"/>
     *               &lt;enumeration value="Peptide-like"/>
     *               &lt;enumeration value="Polycyclic"/>
     *               &lt;enumeration value="Polypeptide"/>
     *               &lt;enumeration value="Polysaccharide"/>
     *               &lt;enumeration value="Quinolone"/>
     *               &lt;enumeration value="Thiolactone"/>
     *               &lt;enumeration value="Thiopeptide"/>
     *               &lt;enumeration value="Siderophore"/>
     *               &lt;enumeration value="Unknown"/>
     *               &lt;enumeration value="Chalkophore, Polypeptide"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/all>
     *       &lt;attribute name="prd_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class PdbxMoleculeFeatures {

        @XmlElementRef(name = "class", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> clazz;
        @XmlElementRef(name = "details", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> details;
        @XmlElementRef(name = "name", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> name;
        @XmlElementRef(name = "type", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> type;
        @XmlAttribute(name = "prd_id", required = true)
        protected String prdId;

        /**
         * Gets the value of the clazz property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getClazz() {
            return clazz;
        }

        /**
         * Sets the value of the clazz property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setClazz(JAXBElement<String> value) {
            this.clazz = value;
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
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setName(JAXBElement<String> value) {
            this.name = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setType(JAXBElement<String> value) {
            this.type = value;
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

    }

}
