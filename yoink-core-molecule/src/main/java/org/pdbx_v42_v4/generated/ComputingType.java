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
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Data items in the COMPUTING category record details about the
 * computer programs used in the crystal structure analysis.
 * 
 * Data items in this category would not, in general, be used in
 * a macromolecular CIF.  The category SOFTWARE, which allows
 * a more detailed description of computer programs and
 * their attributes to be given, would be used instead.
 * 
 *     Example 1 - Rodr\'iguez-Romera, Ruiz-P\'erez & Solans [Acta
 *                 Cryst. (1996), C52, 1415-1417].
 * <PDBx:computingCategory>
 *    <PDBx:computing entry_id="1ABC">
 *       <PDBx:cell_refinement>CAD-4 (Enraf-Nonius, 1989)</PDBx:cell_refinement>
 *       <PDBx:data_collection>CAD-4 (Enraf-Nonius, 1989)</PDBx:data_collection>
 *       <PDBx:data_reduction>CFEO (Solans, 1978)</PDBx:data_reduction>
 *       <PDBx:molecular_graphics>ORTEPII (Johnson, 1976)</PDBx:molecular_graphics>
 *       <PDBx:publication_material>PARST (Nardelli, 1983)</PDBx:publication_material>
 *       <PDBx:structure_refinement>SHELXL93 (Sheldrick, 1993)</PDBx:structure_refinement>
 *       <PDBx:structure_solution>SHELXS86 (Sheldrick, 1990)</PDBx:structure_solution>
 *    </PDBx:computing>
 * </PDBx:computingCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for computingType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="computingType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="computing" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="cell_refinement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="data_collection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="data_reduction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="molecular_graphics" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pdbx_data_reduction_ds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pdbx_data_reduction_ii" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="pdbx_structure_refinement_method" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="publication_material" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="structure_refinement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="structure_solution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="entry_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "computingType", propOrder = {
    "computing"
})
public class ComputingType {

    protected List<ComputingType.Computing> computing;

    /**
     * Gets the value of the computing property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the computing property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getComputing().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ComputingType.Computing }
     * 
     * 
     */
    public List<ComputingType.Computing> getComputing() {
        if (computing == null) {
            computing = new ArrayList<ComputingType.Computing>();
        }
        return this.computing;
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
     *         &lt;element name="cell_refinement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="data_collection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="data_reduction" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="molecular_graphics" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pdbx_data_reduction_ds" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pdbx_data_reduction_ii" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="pdbx_structure_refinement_method" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="publication_material" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="structure_refinement" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="structure_solution" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="entry_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class Computing {

        @XmlElementRef(name = "cell_refinement", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> cellRefinement;
        @XmlElementRef(name = "data_collection", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> dataCollection;
        @XmlElementRef(name = "data_reduction", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> dataReduction;
        @XmlElementRef(name = "molecular_graphics", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> molecularGraphics;
        @XmlElementRef(name = "pdbx_data_reduction_ds", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> pdbxDataReductionDs;
        @XmlElementRef(name = "pdbx_data_reduction_ii", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> pdbxDataReductionIi;
        @XmlElementRef(name = "pdbx_structure_refinement_method", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> pdbxStructureRefinementMethod;
        @XmlElementRef(name = "publication_material", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> publicationMaterial;
        @XmlElementRef(name = "structure_refinement", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> structureRefinement;
        @XmlElementRef(name = "structure_solution", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> structureSolution;
        @XmlAttribute(name = "entry_id", required = true)
        protected String entryId;

        /**
         * Gets the value of the cellRefinement property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getCellRefinement() {
            return cellRefinement;
        }

        /**
         * Sets the value of the cellRefinement property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setCellRefinement(JAXBElement<String> value) {
            this.cellRefinement = value;
        }

        /**
         * Gets the value of the dataCollection property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getDataCollection() {
            return dataCollection;
        }

        /**
         * Sets the value of the dataCollection property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setDataCollection(JAXBElement<String> value) {
            this.dataCollection = value;
        }

        /**
         * Gets the value of the dataReduction property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getDataReduction() {
            return dataReduction;
        }

        /**
         * Sets the value of the dataReduction property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setDataReduction(JAXBElement<String> value) {
            this.dataReduction = value;
        }

        /**
         * Gets the value of the molecularGraphics property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getMolecularGraphics() {
            return molecularGraphics;
        }

        /**
         * Sets the value of the molecularGraphics property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setMolecularGraphics(JAXBElement<String> value) {
            this.molecularGraphics = value;
        }

        /**
         * Gets the value of the pdbxDataReductionDs property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPdbxDataReductionDs() {
            return pdbxDataReductionDs;
        }

        /**
         * Sets the value of the pdbxDataReductionDs property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPdbxDataReductionDs(JAXBElement<String> value) {
            this.pdbxDataReductionDs = value;
        }

        /**
         * Gets the value of the pdbxDataReductionIi property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPdbxDataReductionIi() {
            return pdbxDataReductionIi;
        }

        /**
         * Sets the value of the pdbxDataReductionIi property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPdbxDataReductionIi(JAXBElement<String> value) {
            this.pdbxDataReductionIi = value;
        }

        /**
         * Gets the value of the pdbxStructureRefinementMethod property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPdbxStructureRefinementMethod() {
            return pdbxStructureRefinementMethod;
        }

        /**
         * Sets the value of the pdbxStructureRefinementMethod property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPdbxStructureRefinementMethod(JAXBElement<String> value) {
            this.pdbxStructureRefinementMethod = value;
        }

        /**
         * Gets the value of the publicationMaterial property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPublicationMaterial() {
            return publicationMaterial;
        }

        /**
         * Sets the value of the publicationMaterial property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPublicationMaterial(JAXBElement<String> value) {
            this.publicationMaterial = value;
        }

        /**
         * Gets the value of the structureRefinement property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getStructureRefinement() {
            return structureRefinement;
        }

        /**
         * Sets the value of the structureRefinement property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setStructureRefinement(JAXBElement<String> value) {
            this.structureRefinement = value;
        }

        /**
         * Gets the value of the structureSolution property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getStructureSolution() {
            return structureSolution;
        }

        /**
         * Sets the value of the structureSolution property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setStructureSolution(JAXBElement<String> value) {
            this.structureSolution = value;
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

    }

}
