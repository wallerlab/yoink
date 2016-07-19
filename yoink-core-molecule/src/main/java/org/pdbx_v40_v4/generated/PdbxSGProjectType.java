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
 * Data items in the PDBX_CONTACT_AUTHOR category record details
 * about the Structural Genomics Project and name and initials
 * for each Center.
 * 
 *     Example 1 -
 * <PDBx:pdbx_SG_projectCategory>
 *    <PDBx:pdbx_SG_project id="1">
 *       <PDBx:full_name_of_center>Berkeley Structural Genomics Center</PDBx:full_name_of_center>
 *       <PDBx:initial_of_center>BSGC</PDBx:initial_of_center>
 *       <PDBx:project_name>PSI, Protein Structure Initiative</PDBx:project_name>
 *    </PDBx:pdbx_SG_project>
 * </PDBx:pdbx_SG_projectCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_SG_projectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_SG_projectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_SG_project" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="full_name_of_center" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Accelerated Technologies Center for Gene to 3D Structure"/>
 *                         &lt;enumeration value="Bacterial targets at IGS-CNRS, France"/>
 *                         &lt;enumeration value="Berkeley Structural Genomics Center"/>
 *                         &lt;enumeration value="Montreal-Kingston Bacterial Structural Genomics Initiative"/>
 *                         &lt;enumeration value="Chaperone-Enabled Studies of Epigenetic Regulation Enzymes"/>
 *                         &lt;enumeration value="Assembly, Dynamics and Evolution of Cell-Cell and Cell-Matrix Adhesions"/>
 *                         &lt;enumeration value="Center for Eukaryotic Structural Genomics"/>
 *                         &lt;enumeration value="Structure, Dynamics and Activation Mechanisms of Chemokine Receptors"/>
 *                         &lt;enumeration value="Center for High-Throughput Structural Biology"/>
 *                         &lt;enumeration value="Center for Structural Genomics of Infectious Diseases"/>
 *                         &lt;enumeration value="Center for Structures of Membrane Proteins"/>
 *                         &lt;enumeration value="GPCR Network"/>
 *                         &lt;enumeration value="Atoms-to-Animals: The Immune Function Network"/>
 *                         &lt;enumeration value="Integrated Center for Structure and Function Innovation"/>
 *                         &lt;enumeration value="Israel Structural Proteomics Center"/>
 *                         &lt;enumeration value="Joint Center for Structural Genomics"/>
 *                         &lt;enumeration value="Midwest Center for Structural Genomics"/>
 *                         &lt;enumeration value="Center for Membrane Proteins of Infectious Diseases"/>
 *                         &lt;enumeration value="Mitochondrial Protein Partnership"/>
 *                         &lt;enumeration value="Membrane Protein Structural Biology Consortium"/>
 *                         &lt;enumeration value="Membrane Protein Structures by Solution NMR"/>
 *                         &lt;enumeration value="Marseilles Structural Genomics Program @ AFMB"/>
 *                         &lt;enumeration value="Medical Structural Genomics of Pathogenic Protozoa"/>
 *                         &lt;enumeration value="Structures of Mtb Proteins Conferring Susceptibility to Known Mtb Inhibitors"/>
 *                         &lt;enumeration value="Enzyme Discovery for Natural Product Biosynthesis"/>
 *                         &lt;enumeration value="Nucleocytoplasmic Transport: a Target for Cellular Control"/>
 *                         &lt;enumeration value="New York Consortium on Membrane Protein Structure"/>
 *                         &lt;enumeration value="New York Structural Genomics Research Consortium"/>
 *                         &lt;enumeration value="New York SGX Research Center for Structural Genomics"/>
 *                         &lt;enumeration value="New York Structural GenomiX Research Consortium"/>
 *                         &lt;enumeration value="Northeast Structural Genomics Consortium"/>
 *                         &lt;enumeration value="Ontario Centre for Structural Proteomics"/>
 *                         &lt;enumeration value="Oxford Protein Production Facility"/>
 *                         &lt;enumeration value="Program for the Characterization of Secreted Effector Proteins"/>
 *                         &lt;enumeration value="Protein Structure Factory"/>
 *                         &lt;enumeration value="RIKEN Structural Genomics/Proteomics Initiative"/>
 *                         &lt;enumeration value="Structure 2 Function Project"/>
 *                         &lt;enumeration value="South Africa Structural Targets Annotation Database"/>
 *                         &lt;enumeration value="Southeast Collaboratory for Structural Genomics"/>
 *                         &lt;enumeration value="Structural Genomics Consortium"/>
 *                         &lt;enumeration value="Structural Genomics Consortium for Research on Gene Expression"/>
 *                         &lt;enumeration value="Structural Genomics of Pathogenic Protozoa Consortium"/>
 *                         &lt;enumeration value="Structural Proteomics in Europe"/>
 *                         &lt;enumeration value="Structural Proteomics in Europe 2"/>
 *                         &lt;enumeration value="Seattle Structural Genomics Center for Infectious Disease"/>
 *                         &lt;enumeration value="Scottish Structural Proteomics Facility"/>
 *                         &lt;enumeration value="Partnership for Stem Cell Biology"/>
 *                         &lt;enumeration value="TB Structural Genomics Consortium"/>
 *                         &lt;enumeration value="Partnership for T-Cell Biology"/>
 *                         &lt;enumeration value="Transcontinental EM Initiative for Membrane Protein Structure"/>
 *                         &lt;enumeration value="Structure-Function Studies of Tight Junction Membrane Proteins"/>
 *                         &lt;enumeration value="Transmembrane Protein Center"/>
 *                         &lt;enumeration value="Center for the X-ray Structure Determination of Human Transporters"/>
 *                         &lt;enumeration value="Structure-Function Analysis of Polymorphic CDI Toxin-Immunity Protein Complexes"/>
 *                         &lt;enumeration value="Mycobacterium Tuberculosis Structural Proteomics Project"/>
 *                         &lt;enumeration value="Paris-Sud Yeast Structural Genomics"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="initial_of_center" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="ATCG3D"/>
 *                         &lt;enumeration value="BIGS"/>
 *                         &lt;enumeration value="BSGC"/>
 *                         &lt;enumeration value="BSGI"/>
 *                         &lt;enumeration value="CEBS"/>
 *                         &lt;enumeration value="CELLMAT"/>
 *                         &lt;enumeration value="CESG"/>
 *                         &lt;enumeration value="CHSAM"/>
 *                         &lt;enumeration value="CHTSB"/>
 *                         &lt;enumeration value="CSGID"/>
 *                         &lt;enumeration value="CSMP"/>
 *                         &lt;enumeration value="GPCR"/>
 *                         &lt;enumeration value="IFN"/>
 *                         &lt;enumeration value="ISFI"/>
 *                         &lt;enumeration value="ISPC"/>
 *                         &lt;enumeration value="JCSG"/>
 *                         &lt;enumeration value="MCSG"/>
 *                         &lt;enumeration value="MPID"/>
 *                         &lt;enumeration value="MPP"/>
 *                         &lt;enumeration value="MPSBC"/>
 *                         &lt;enumeration value="MPSbyNMR"/>
 *                         &lt;enumeration value="MSGP"/>
 *                         &lt;enumeration value="MSGPP"/>
 *                         &lt;enumeration value="MTBI"/>
 *                         &lt;enumeration value="NatPro"/>
 *                         &lt;enumeration value="NPCXstals"/>
 *                         &lt;enumeration value="NYCOMPS"/>
 *                         &lt;enumeration value="NYSGRC"/>
 *                         &lt;enumeration value="NYSGXRC"/>
 *                         &lt;enumeration value="NESG"/>
 *                         &lt;enumeration value="OCSP"/>
 *                         &lt;enumeration value="OPPF"/>
 *                         &lt;enumeration value="PCSEP"/>
 *                         &lt;enumeration value="PSF"/>
 *                         &lt;enumeration value="RSGI"/>
 *                         &lt;enumeration value="S2F"/>
 *                         &lt;enumeration value="SASTAD"/>
 *                         &lt;enumeration value="SECSG"/>
 *                         &lt;enumeration value="SGC"/>
 *                         &lt;enumeration value="SGCGES"/>
 *                         &lt;enumeration value="SGPP"/>
 *                         &lt;enumeration value="SPINE"/>
 *                         &lt;enumeration value="SPINE-2"/>
 *                         &lt;enumeration value="SSGCID"/>
 *                         &lt;enumeration value="SSPF"/>
 *                         &lt;enumeration value="STEMCELL"/>
 *                         &lt;enumeration value="TBSGC"/>
 *                         &lt;enumeration value="TCELL"/>
 *                         &lt;enumeration value="TEMIMPS"/>
 *                         &lt;enumeration value="TJMP"/>
 *                         &lt;enumeration value="TMPC"/>
 *                         &lt;enumeration value="TransportPDB"/>
 *                         &lt;enumeration value="UC4CDI"/>
 *                         &lt;enumeration value="XMTB"/>
 *                         &lt;enumeration value="YSG"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="project_name" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="PSI:Biology"/>
 *                         &lt;enumeration value="PSI, Protein Structure Initiative"/>
 *                         &lt;enumeration value="NIAID, National Institute of Allergy and Infectious Diseases"/>
 *                         &lt;enumeration value="NPPSFA, National Project on Protein Structural and Functional Analyses"/>
 *                         &lt;enumeration value="Enzyme Function Initiative"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *                 &lt;attribute name="id" use="required">
 *                   &lt;simpleType>
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                       &lt;enumeration value="1"/>
 *                       &lt;enumeration value="2"/>
 *                       &lt;enumeration value="3"/>
 *                       &lt;enumeration value="4"/>
 *                       &lt;enumeration value="5"/>
 *                       &lt;enumeration value="6"/>
 *                       &lt;enumeration value="7"/>
 *                       &lt;enumeration value="8"/>
 *                       &lt;enumeration value="9"/>
 *                       &lt;enumeration value="10"/>
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
@XmlType(name = "pdbx_SG_projectType", propOrder = {
    "pdbxSGProject"
})
public class PdbxSGProjectType {

    @XmlElement(name = "pdbx_SG_project")
    protected List<PdbxSGProjectType.PdbxSGProject> pdbxSGProject;

    /**
     * Gets the value of the pdbxSGProject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxSGProject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxSGProject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxSGProjectType.PdbxSGProject }
     * 
     * 
     */
    public List<PdbxSGProjectType.PdbxSGProject> getPdbxSGProject() {
        if (pdbxSGProject == null) {
            pdbxSGProject = new ArrayList<PdbxSGProjectType.PdbxSGProject>();
        }
        return this.pdbxSGProject;
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
     *         &lt;element name="full_name_of_center" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Accelerated Technologies Center for Gene to 3D Structure"/>
     *               &lt;enumeration value="Bacterial targets at IGS-CNRS, France"/>
     *               &lt;enumeration value="Berkeley Structural Genomics Center"/>
     *               &lt;enumeration value="Montreal-Kingston Bacterial Structural Genomics Initiative"/>
     *               &lt;enumeration value="Chaperone-Enabled Studies of Epigenetic Regulation Enzymes"/>
     *               &lt;enumeration value="Assembly, Dynamics and Evolution of Cell-Cell and Cell-Matrix Adhesions"/>
     *               &lt;enumeration value="Center for Eukaryotic Structural Genomics"/>
     *               &lt;enumeration value="Structure, Dynamics and Activation Mechanisms of Chemokine Receptors"/>
     *               &lt;enumeration value="Center for High-Throughput Structural Biology"/>
     *               &lt;enumeration value="Center for Structural Genomics of Infectious Diseases"/>
     *               &lt;enumeration value="Center for Structures of Membrane Proteins"/>
     *               &lt;enumeration value="GPCR Network"/>
     *               &lt;enumeration value="Atoms-to-Animals: The Immune Function Network"/>
     *               &lt;enumeration value="Integrated Center for Structure and Function Innovation"/>
     *               &lt;enumeration value="Israel Structural Proteomics Center"/>
     *               &lt;enumeration value="Joint Center for Structural Genomics"/>
     *               &lt;enumeration value="Midwest Center for Structural Genomics"/>
     *               &lt;enumeration value="Center for Membrane Proteins of Infectious Diseases"/>
     *               &lt;enumeration value="Mitochondrial Protein Partnership"/>
     *               &lt;enumeration value="Membrane Protein Structural Biology Consortium"/>
     *               &lt;enumeration value="Membrane Protein Structures by Solution NMR"/>
     *               &lt;enumeration value="Marseilles Structural Genomics Program @ AFMB"/>
     *               &lt;enumeration value="Medical Structural Genomics of Pathogenic Protozoa"/>
     *               &lt;enumeration value="Structures of Mtb Proteins Conferring Susceptibility to Known Mtb Inhibitors"/>
     *               &lt;enumeration value="Enzyme Discovery for Natural Product Biosynthesis"/>
     *               &lt;enumeration value="Nucleocytoplasmic Transport: a Target for Cellular Control"/>
     *               &lt;enumeration value="New York Consortium on Membrane Protein Structure"/>
     *               &lt;enumeration value="New York Structural Genomics Research Consortium"/>
     *               &lt;enumeration value="New York SGX Research Center for Structural Genomics"/>
     *               &lt;enumeration value="New York Structural GenomiX Research Consortium"/>
     *               &lt;enumeration value="Northeast Structural Genomics Consortium"/>
     *               &lt;enumeration value="Ontario Centre for Structural Proteomics"/>
     *               &lt;enumeration value="Oxford Protein Production Facility"/>
     *               &lt;enumeration value="Program for the Characterization of Secreted Effector Proteins"/>
     *               &lt;enumeration value="Protein Structure Factory"/>
     *               &lt;enumeration value="RIKEN Structural Genomics/Proteomics Initiative"/>
     *               &lt;enumeration value="Structure 2 Function Project"/>
     *               &lt;enumeration value="South Africa Structural Targets Annotation Database"/>
     *               &lt;enumeration value="Southeast Collaboratory for Structural Genomics"/>
     *               &lt;enumeration value="Structural Genomics Consortium"/>
     *               &lt;enumeration value="Structural Genomics Consortium for Research on Gene Expression"/>
     *               &lt;enumeration value="Structural Genomics of Pathogenic Protozoa Consortium"/>
     *               &lt;enumeration value="Structural Proteomics in Europe"/>
     *               &lt;enumeration value="Structural Proteomics in Europe 2"/>
     *               &lt;enumeration value="Seattle Structural Genomics Center for Infectious Disease"/>
     *               &lt;enumeration value="Scottish Structural Proteomics Facility"/>
     *               &lt;enumeration value="Partnership for Stem Cell Biology"/>
     *               &lt;enumeration value="TB Structural Genomics Consortium"/>
     *               &lt;enumeration value="Partnership for T-Cell Biology"/>
     *               &lt;enumeration value="Transcontinental EM Initiative for Membrane Protein Structure"/>
     *               &lt;enumeration value="Structure-Function Studies of Tight Junction Membrane Proteins"/>
     *               &lt;enumeration value="Transmembrane Protein Center"/>
     *               &lt;enumeration value="Center for the X-ray Structure Determination of Human Transporters"/>
     *               &lt;enumeration value="Structure-Function Analysis of Polymorphic CDI Toxin-Immunity Protein Complexes"/>
     *               &lt;enumeration value="Mycobacterium Tuberculosis Structural Proteomics Project"/>
     *               &lt;enumeration value="Paris-Sud Yeast Structural Genomics"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="initial_of_center" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="ATCG3D"/>
     *               &lt;enumeration value="BIGS"/>
     *               &lt;enumeration value="BSGC"/>
     *               &lt;enumeration value="BSGI"/>
     *               &lt;enumeration value="CEBS"/>
     *               &lt;enumeration value="CELLMAT"/>
     *               &lt;enumeration value="CESG"/>
     *               &lt;enumeration value="CHSAM"/>
     *               &lt;enumeration value="CHTSB"/>
     *               &lt;enumeration value="CSGID"/>
     *               &lt;enumeration value="CSMP"/>
     *               &lt;enumeration value="GPCR"/>
     *               &lt;enumeration value="IFN"/>
     *               &lt;enumeration value="ISFI"/>
     *               &lt;enumeration value="ISPC"/>
     *               &lt;enumeration value="JCSG"/>
     *               &lt;enumeration value="MCSG"/>
     *               &lt;enumeration value="MPID"/>
     *               &lt;enumeration value="MPP"/>
     *               &lt;enumeration value="MPSBC"/>
     *               &lt;enumeration value="MPSbyNMR"/>
     *               &lt;enumeration value="MSGP"/>
     *               &lt;enumeration value="MSGPP"/>
     *               &lt;enumeration value="MTBI"/>
     *               &lt;enumeration value="NatPro"/>
     *               &lt;enumeration value="NPCXstals"/>
     *               &lt;enumeration value="NYCOMPS"/>
     *               &lt;enumeration value="NYSGRC"/>
     *               &lt;enumeration value="NYSGXRC"/>
     *               &lt;enumeration value="NESG"/>
     *               &lt;enumeration value="OCSP"/>
     *               &lt;enumeration value="OPPF"/>
     *               &lt;enumeration value="PCSEP"/>
     *               &lt;enumeration value="PSF"/>
     *               &lt;enumeration value="RSGI"/>
     *               &lt;enumeration value="S2F"/>
     *               &lt;enumeration value="SASTAD"/>
     *               &lt;enumeration value="SECSG"/>
     *               &lt;enumeration value="SGC"/>
     *               &lt;enumeration value="SGCGES"/>
     *               &lt;enumeration value="SGPP"/>
     *               &lt;enumeration value="SPINE"/>
     *               &lt;enumeration value="SPINE-2"/>
     *               &lt;enumeration value="SSGCID"/>
     *               &lt;enumeration value="SSPF"/>
     *               &lt;enumeration value="STEMCELL"/>
     *               &lt;enumeration value="TBSGC"/>
     *               &lt;enumeration value="TCELL"/>
     *               &lt;enumeration value="TEMIMPS"/>
     *               &lt;enumeration value="TJMP"/>
     *               &lt;enumeration value="TMPC"/>
     *               &lt;enumeration value="TransportPDB"/>
     *               &lt;enumeration value="UC4CDI"/>
     *               &lt;enumeration value="XMTB"/>
     *               &lt;enumeration value="YSG"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="project_name" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="PSI:Biology"/>
     *               &lt;enumeration value="PSI, Protein Structure Initiative"/>
     *               &lt;enumeration value="NIAID, National Institute of Allergy and Infectious Diseases"/>
     *               &lt;enumeration value="NPPSFA, National Project on Protein Structural and Functional Analyses"/>
     *               &lt;enumeration value="Enzyme Function Initiative"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/all>
     *       &lt;attribute name="id" use="required">
     *         &lt;simpleType>
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *             &lt;enumeration value="1"/>
     *             &lt;enumeration value="2"/>
     *             &lt;enumeration value="3"/>
     *             &lt;enumeration value="4"/>
     *             &lt;enumeration value="5"/>
     *             &lt;enumeration value="6"/>
     *             &lt;enumeration value="7"/>
     *             &lt;enumeration value="8"/>
     *             &lt;enumeration value="9"/>
     *             &lt;enumeration value="10"/>
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
    public static class PdbxSGProject {

        @XmlElementRef(name = "full_name_of_center", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> fullNameOfCenter;
        @XmlElementRef(name = "initial_of_center", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> initialOfCenter;
        @XmlElementRef(name = "project_name", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> projectName;
        @XmlAttribute(name = "id", required = true)
        protected BigInteger id;

        /**
         * Gets the value of the fullNameOfCenter property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getFullNameOfCenter() {
            return fullNameOfCenter;
        }

        /**
         * Sets the value of the fullNameOfCenter property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setFullNameOfCenter(JAXBElement<String> value) {
            this.fullNameOfCenter = value;
        }

        /**
         * Gets the value of the initialOfCenter property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getInitialOfCenter() {
            return initialOfCenter;
        }

        /**
         * Sets the value of the initialOfCenter property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setInitialOfCenter(JAXBElement<String> value) {
            this.initialOfCenter = value;
        }

        /**
         * Gets the value of the projectName property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getProjectName() {
            return projectName;
        }

        /**
         * Sets the value of the projectName property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setProjectName(JAXBElement<String> value) {
            this.projectName = value;
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
