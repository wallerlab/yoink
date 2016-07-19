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
 * Contains all the data items that refer to the space group as a
 * whole, such as its name or crystal system. They may be looped,
 * for example, in a list of space groups and their properties.
 * 
 * Only a subset of the SPACE_GROUP category items appear in
 * this dictionary.  The remainder are found in the symmetry CIF
 * dictionary.
 * 
 * Space-group types are identified by their number as given in
 * International Tables for Crystallography Vol. A. Specific
 * settings of the space groups can be identified either by their
 * Hall symbol or by specifying their symmetry operations.
 * 
 * The commonly used Hermann-Mauguin symbol determines the
 * space-group type uniquely but several different Hermann-Mauguin
 * symbols may refer to the same space-group type. A Hermann-Mauguin
 * symbol contains information on the choice of the basis, but not
 * on the choice of origin.  Different formats for the
 * Hermann-Mauguin symbol are found in the symmetry CIF dictionary.
 * 
 *     Example 1 - the monoclinic space group No. 15 with unique axis b.
 * <PDBx:space_groupCategory>
 *    <PDBx:space_group id="1">
 *       <PDBx:IT_number>15</PDBx:IT_number>
 *       <PDBx:crystal_system>monoclinic</PDBx:crystal_system>
 *       <PDBx:name_H-M_alt>C 2/c</PDBx:name_H-M_alt>
 *       <PDBx:name_Hall>-C 2yc</PDBx:name_Hall>
 *    </PDBx:space_group>
 * </PDBx:space_groupCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for space_groupType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="space_groupType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="space_group" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="IT_number" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="1"/>
 *                         &lt;maxInclusive value="230"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="crystal_system" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="triclinic"/>
 *                         &lt;enumeration value="monoclinic"/>
 *                         &lt;enumeration value="orthorhombic"/>
 *                         &lt;enumeration value="tetragonal"/>
 *                         &lt;enumeration value="trigonal"/>
 *                         &lt;enumeration value="hexagonal"/>
 *                         &lt;enumeration value="cubic"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="name_H-M_alt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="name_Hall" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "space_groupType", propOrder = {
    "spaceGroup"
})
public class SpaceGroupType {

    @XmlElement(name = "space_group")
    protected List<SpaceGroupType.SpaceGroup> spaceGroup;

    /**
     * Gets the value of the spaceGroup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the spaceGroup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSpaceGroup().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SpaceGroupType.SpaceGroup }
     * 
     * 
     */
    public List<SpaceGroupType.SpaceGroup> getSpaceGroup() {
        if (spaceGroup == null) {
            spaceGroup = new ArrayList<SpaceGroupType.SpaceGroup>();
        }
        return this.spaceGroup;
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
     *         &lt;element name="IT_number" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="1"/>
     *               &lt;maxInclusive value="230"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="crystal_system" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="triclinic"/>
     *               &lt;enumeration value="monoclinic"/>
     *               &lt;enumeration value="orthorhombic"/>
     *               &lt;enumeration value="tetragonal"/>
     *               &lt;enumeration value="trigonal"/>
     *               &lt;enumeration value="hexagonal"/>
     *               &lt;enumeration value="cubic"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="name_H-M_alt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="name_Hall" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    public static class SpaceGroup {

        @XmlElementRef(name = "IT_number", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<Integer> itNumber;
        @XmlElementRef(name = "crystal_system", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> crystalSystem;
        @XmlElementRef(name = "name_H-M_alt", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> nameHMAlt;
        @XmlElementRef(name = "name_Hall", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> nameHall;
        @XmlAttribute(name = "id", required = true)
        protected String id;

        /**
         * Gets the value of the itNumber property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
         *     
         */
        public JAXBElement<Integer> getITNumber() {
            return itNumber;
        }

        /**
         * Sets the value of the itNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link Integer }{@code >}
         *     
         */
        public void setITNumber(JAXBElement<Integer> value) {
            this.itNumber = value;
        }

        /**
         * Gets the value of the crystalSystem property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getCrystalSystem() {
            return crystalSystem;
        }

        /**
         * Sets the value of the crystalSystem property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setCrystalSystem(JAXBElement<String> value) {
            this.crystalSystem = value;
        }

        /**
         * Gets the value of the nameHMAlt property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getNameHMAlt() {
            return nameHMAlt;
        }

        /**
         * Sets the value of the nameHMAlt property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setNameHMAlt(JAXBElement<String> value) {
            this.nameHMAlt = value;
        }

        /**
         * Gets the value of the nameHall property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getNameHall() {
            return nameHall;
        }

        /**
         * Sets the value of the nameHall property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setNameHall(JAXBElement<String> value) {
            this.nameHall = value;
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
