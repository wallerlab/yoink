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
 * Data items in the PDBX_REFERENCE_MOLECULE category record
 * reference information about small polymer molecules.
 * 
 *     Example: 1  Actinomycin
 * <PDBx:pdbx_reference_moleculeCategory>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000001">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C62 H86 N12 O16</PDBx:formula>
 *       <PDBx:formula_weight>1255.5</PDBx:formula_weight>
 *       <PDBx:name>Actinomycin D</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000002">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:name>Actinomycin C</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000003">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C63 H88 N12 O16</PDBx:formula>
 *       <PDBx:formula_weight>1269.5</PDBx:formula_weight>
 *       <PDBx:name>Actinomycin C2</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000004">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C64 H90 N12 O16</PDBx:formula>
 *       <PDBx:formula_weight>1283.5</PDBx:formula_weight>
 *       <PDBx:name>Actinomycin C3</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000005">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C62 H86 N12 O17</PDBx:formula>
 *       <PDBx:formula_weight>1271.5</PDBx:formula_weight>
 *       <PDBx:name>Actinomycin X0 beta</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000006">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C62 H85 F N12 O16</PDBx:formula>
 *       <PDBx:formula_weight>1273.49</PDBx:formula_weight>
 *       <PDBx:name>8-Fluoro-Actinomycin D</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000007">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C62 H84 N12 O17</PDBx:formula>
 *       <PDBx:formula_weight>1269.4</PDBx:formula_weight>
 *       <PDBx:name>Actinomycin X2</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000008">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C62 H84 N12 O19</PDBx:formula>
 *       <PDBx:formula_weight>1301.5</PDBx:formula_weight>
 *       <PDBx:name>Actinomycin Z1</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000009">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C62 H83 Cl N12 O18</PDBx:formula>
 *       <PDBx:formula_weight>1319.845</PDBx:formula_weight>
 *       <PDBx:name>Actinomycin Z3</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000010">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C62 H87 N13 O16</PDBx:formula>
 *       <PDBx:formula_weight>1270.43</PDBx:formula_weight>
 *       <PDBx:name>7-AminoActinomycin</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 *    <PDBx:pdbx_reference_molecule prd_id="PRD_000011">
 *       <PDBx:class>polypeptide antibiotic</PDBx:class>
 *       <PDBx:formula>C61 H85 N13 O16</PDBx:formula>
 *       <PDBx:formula_weight>1256.4051</PDBx:formula_weight>
 *       <PDBx:name>N8-Actinomycin D</PDBx:name>
 *       <PDBx:type>Antitumor Antibiotic</PDBx:type>
 *    </PDBx:pdbx_reference_molecule>
 * </PDBx:pdbx_reference_moleculeCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_reference_moleculeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_reference_moleculeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_reference_molecule" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="chem_comp_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *                   &lt;element name="class_evidence_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="compound_details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="formula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="formula_weight" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;union>
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                             &lt;minExclusive value="1.0"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                             &lt;minInclusive value="1.0"/>
 *                             &lt;maxInclusive value="1.0"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/union>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="release_status" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="REL"/>
 *                         &lt;enumeration value="HOLD"/>
 *                         &lt;enumeration value="OBS"/>
 *                         &lt;enumeration value="WAIT"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="replaced_by" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="replaces" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="represent_as" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="polymer"/>
 *                         &lt;enumeration value="single molecule"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="representative_PDB_id_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
 *                   &lt;element name="type_evidence_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "pdbx_reference_moleculeType", propOrder = {
    "pdbxReferenceMolecule"
})
public class PdbxReferenceMoleculeType {

    @XmlElement(name = "pdbx_reference_molecule")
    protected List<PdbxReferenceMoleculeType.PdbxReferenceMolecule> pdbxReferenceMolecule;

    /**
     * Gets the value of the pdbxReferenceMolecule property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxReferenceMolecule property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxReferenceMolecule().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxReferenceMoleculeType.PdbxReferenceMolecule }
     * 
     * 
     */
    public List<PdbxReferenceMoleculeType.PdbxReferenceMolecule> getPdbxReferenceMolecule() {
        if (pdbxReferenceMolecule == null) {
            pdbxReferenceMolecule = new ArrayList<PdbxReferenceMoleculeType.PdbxReferenceMolecule>();
        }
        return this.pdbxReferenceMolecule;
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
     *         &lt;element name="chem_comp_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *         &lt;element name="class_evidence_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="compound_details" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="formula" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="formula_weight" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;union>
     *               &lt;simpleType>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                   &lt;minExclusive value="1.0"/>
     *                 &lt;/restriction>
     *               &lt;/simpleType>
     *               &lt;simpleType>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                   &lt;minInclusive value="1.0"/>
     *                   &lt;maxInclusive value="1.0"/>
     *                 &lt;/restriction>
     *               &lt;/simpleType>
     *             &lt;/union>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="release_status" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="REL"/>
     *               &lt;enumeration value="HOLD"/>
     *               &lt;enumeration value="OBS"/>
     *               &lt;enumeration value="WAIT"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="replaced_by" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="replaces" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="represent_as" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="polymer"/>
     *               &lt;enumeration value="single molecule"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="representative_PDB_id_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
     *         &lt;element name="type_evidence_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    public static class PdbxReferenceMolecule {

        @XmlElementRef(name = "chem_comp_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> chemCompId;
        @XmlElementRef(name = "class", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> clazz;
        @XmlElementRef(name = "class_evidence_code", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> classEvidenceCode;
        @XmlElementRef(name = "compound_details", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> compoundDetails;
        @XmlElementRef(name = "description", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> description;
        @XmlElementRef(name = "formula", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> formula;
        @XmlElementRef(name = "formula_weight", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> formulaWeight;
        @XmlElementRef(name = "name", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> name;
        @XmlElementRef(name = "release_status", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> releaseStatus;
        @XmlElementRef(name = "replaced_by", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> replacedBy;
        @XmlElementRef(name = "replaces", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> replaces;
        @XmlElementRef(name = "represent_as", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> representAs;
        @XmlElementRef(name = "representative_PDB_id_code", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> representativePDBIdCode;
        @XmlElementRef(name = "type", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> type;
        @XmlElementRef(name = "type_evidence_code", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> typeEvidenceCode;
        @XmlAttribute(name = "prd_id", required = true)
        protected String prdId;

        /**
         * Gets the value of the chemCompId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getChemCompId() {
            return chemCompId;
        }

        /**
         * Sets the value of the chemCompId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setChemCompId(JAXBElement<String> value) {
            this.chemCompId = value;
        }

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
         * Gets the value of the classEvidenceCode property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getClassEvidenceCode() {
            return classEvidenceCode;
        }

        /**
         * Sets the value of the classEvidenceCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setClassEvidenceCode(JAXBElement<String> value) {
            this.classEvidenceCode = value;
        }

        /**
         * Gets the value of the compoundDetails property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getCompoundDetails() {
            return compoundDetails;
        }

        /**
         * Sets the value of the compoundDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setCompoundDetails(JAXBElement<String> value) {
            this.compoundDetails = value;
        }

        /**
         * Gets the value of the description property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getDescription() {
            return description;
        }

        /**
         * Sets the value of the description property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setDescription(JAXBElement<String> value) {
            this.description = value;
        }

        /**
         * Gets the value of the formula property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getFormula() {
            return formula;
        }

        /**
         * Sets the value of the formula property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setFormula(JAXBElement<String> value) {
            this.formula = value;
        }

        /**
         * Gets the value of the formulaWeight property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getFormulaWeight() {
            return formulaWeight;
        }

        /**
         * Sets the value of the formulaWeight property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setFormulaWeight(JAXBElement<String> value) {
            this.formulaWeight = value;
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
         * Gets the value of the releaseStatus property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getReleaseStatus() {
            return releaseStatus;
        }

        /**
         * Sets the value of the releaseStatus property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setReleaseStatus(JAXBElement<String> value) {
            this.releaseStatus = value;
        }

        /**
         * Gets the value of the replacedBy property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getReplacedBy() {
            return replacedBy;
        }

        /**
         * Sets the value of the replacedBy property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setReplacedBy(JAXBElement<String> value) {
            this.replacedBy = value;
        }

        /**
         * Gets the value of the replaces property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getReplaces() {
            return replaces;
        }

        /**
         * Sets the value of the replaces property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setReplaces(JAXBElement<String> value) {
            this.replaces = value;
        }

        /**
         * Gets the value of the representAs property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getRepresentAs() {
            return representAs;
        }

        /**
         * Sets the value of the representAs property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setRepresentAs(JAXBElement<String> value) {
            this.representAs = value;
        }

        /**
         * Gets the value of the representativePDBIdCode property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getRepresentativePDBIdCode() {
            return representativePDBIdCode;
        }

        /**
         * Sets the value of the representativePDBIdCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setRepresentativePDBIdCode(JAXBElement<String> value) {
            this.representativePDBIdCode = value;
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
         * Gets the value of the typeEvidenceCode property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getTypeEvidenceCode() {
            return typeEvidenceCode;
        }

        /**
         * Sets the value of the typeEvidenceCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setTypeEvidenceCode(JAXBElement<String> value) {
            this.typeEvidenceCode = value;
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
