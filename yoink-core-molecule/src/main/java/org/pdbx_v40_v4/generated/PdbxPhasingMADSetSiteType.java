//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.22 at 05:23:04 PM EST 
//


package org.gradle.pdbml.v40.generated;

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
 * record the details (coordinates etc.) of anomalous scatters.
 * 
 * 
 * 
 *     Example 1 -  anomalous scatters is Se
 * <PDBx:pdbx_phasing_MAD_set_siteCategory>
 *    <PDBx:pdbx_phasing_MAD_set_site id="1">
 *       <PDBx:Cartn_x>25.9407</PDBx:Cartn_x>
 *       <PDBx:Cartn_y>-0.103471</PDBx:Cartn_y>
 *       <PDBx:Cartn_z>17.4094</PDBx:Cartn_z>
 *       <PDBx:atom_type_symbol>SE</PDBx:atom_type_symbol>
 *       <PDBx:b_iso>15.2561</PDBx:b_iso>
 *       <PDBx:occupancy>1</PDBx:occupancy>
 *    </PDBx:pdbx_phasing_MAD_set_site>
 *    <PDBx:pdbx_phasing_MAD_set_site id="2">
 *       <PDBx:Cartn_x>30.6534</PDBx:Cartn_x>
 *       <PDBx:Cartn_y>6.62359</PDBx:Cartn_y>
 *       <PDBx:Cartn_z>9.93063</PDBx:Cartn_z>
 *       <PDBx:atom_type_symbol>SE</PDBx:atom_type_symbol>
 *       <PDBx:b_iso>12.9102</PDBx:b_iso>
 *       <PDBx:occupancy>1</PDBx:occupancy>
 *    </PDBx:pdbx_phasing_MAD_set_site>
 *    <PDBx:pdbx_phasing_MAD_set_site id="3">
 *       <PDBx:Cartn_x>-3.26506</PDBx:Cartn_x>
 *       <PDBx:Cartn_y>15.5546</PDBx:Cartn_y>
 *       <PDBx:Cartn_z>53.9529</PDBx:Cartn_z>
 *       <PDBx:atom_type_symbol>SE</PDBx:atom_type_symbol>
 *       <PDBx:b_iso>30.5239</PDBx:b_iso>
 *       <PDBx:occupancy>1</PDBx:occupancy>
 *    </PDBx:pdbx_phasing_MAD_set_site>
 * </PDBx:pdbx_phasing_MAD_set_siteCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_phasing_MAD_set_siteType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_phasing_MAD_set_siteType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_phasing_MAD_set_site" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="Cartn_x" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Cartn_x_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Cartn_y" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Cartn_y_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Cartn_z" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="Cartn_z_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="atom_type_symbol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="b_iso" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="b_iso_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fract_x" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fract_x_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fract_y" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fract_y_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fract_z" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fract_z_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="occupancy" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="occupancy_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="occupancy_iso" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="set_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "pdbx_phasing_MAD_set_siteType", propOrder = {
    "pdbxPhasingMADSetSite"
})
public class PdbxPhasingMADSetSiteType {

    @XmlElement(name = "pdbx_phasing_MAD_set_site")
    protected List<PdbxPhasingMADSetSiteType.PdbxPhasingMADSetSite> pdbxPhasingMADSetSite;

    /**
     * Gets the value of the pdbxPhasingMADSetSite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxPhasingMADSetSite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxPhasingMADSetSite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxPhasingMADSetSiteType.PdbxPhasingMADSetSite }
     * 
     * 
     */
    public List<PdbxPhasingMADSetSiteType.PdbxPhasingMADSetSite> getPdbxPhasingMADSetSite() {
        if (pdbxPhasingMADSetSite == null) {
            pdbxPhasingMADSetSite = new ArrayList<PdbxPhasingMADSetSiteType.PdbxPhasingMADSetSite>();
        }
        return this.pdbxPhasingMADSetSite;
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
     *         &lt;element name="Cartn_x" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Cartn_x_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Cartn_y" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Cartn_y_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Cartn_z" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="Cartn_z_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="atom_type_symbol" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="b_iso" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="b_iso_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fract_x" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fract_x_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fract_y" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fract_y_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fract_z" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fract_z_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="occupancy" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="occupancy_esd" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="occupancy_iso" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="set_id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    public static class PdbxPhasingMADSetSite {

        @XmlElementRef(name = "Cartn_x", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> cartnX;
        @XmlElementRef(name = "Cartn_x_esd", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> cartnXEsd;
        @XmlElementRef(name = "Cartn_y", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> cartnY;
        @XmlElementRef(name = "Cartn_y_esd", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> cartnYEsd;
        @XmlElementRef(name = "Cartn_z", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> cartnZ;
        @XmlElementRef(name = "Cartn_z_esd", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> cartnZEsd;
        @XmlElementRef(name = "atom_type_symbol", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> atomTypeSymbol;
        @XmlElementRef(name = "b_iso", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> bIso;
        @XmlElementRef(name = "b_iso_esd", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> bIsoEsd;
        @XmlElementRef(name = "fract_x", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fractX;
        @XmlElementRef(name = "fract_x_esd", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fractXEsd;
        @XmlElementRef(name = "fract_y", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fractY;
        @XmlElementRef(name = "fract_y_esd", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fractYEsd;
        @XmlElementRef(name = "fract_z", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fractZ;
        @XmlElementRef(name = "fract_z_esd", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fractZEsd;
        @XmlElementRef(name = "occupancy", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> occupancy;
        @XmlElementRef(name = "occupancy_esd", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> occupancyEsd;
        @XmlElementRef(name = "occupancy_iso", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> occupancyIso;
        @XmlElementRef(name = "set_id", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> setId;
        @XmlAttribute(name = "id", required = true)
        protected String id;

        /**
         * Gets the value of the cartnX property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getCartnX() {
            return cartnX;
        }

        /**
         * Sets the value of the cartnX property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setCartnX(JAXBElement<BigDecimal> value) {
            this.cartnX = value;
        }

        /**
         * Gets the value of the cartnXEsd property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getCartnXEsd() {
            return cartnXEsd;
        }

        /**
         * Sets the value of the cartnXEsd property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setCartnXEsd(JAXBElement<BigDecimal> value) {
            this.cartnXEsd = value;
        }

        /**
         * Gets the value of the cartnY property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getCartnY() {
            return cartnY;
        }

        /**
         * Sets the value of the cartnY property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setCartnY(JAXBElement<BigDecimal> value) {
            this.cartnY = value;
        }

        /**
         * Gets the value of the cartnYEsd property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getCartnYEsd() {
            return cartnYEsd;
        }

        /**
         * Sets the value of the cartnYEsd property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setCartnYEsd(JAXBElement<BigDecimal> value) {
            this.cartnYEsd = value;
        }

        /**
         * Gets the value of the cartnZ property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getCartnZ() {
            return cartnZ;
        }

        /**
         * Sets the value of the cartnZ property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setCartnZ(JAXBElement<BigDecimal> value) {
            this.cartnZ = value;
        }

        /**
         * Gets the value of the cartnZEsd property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getCartnZEsd() {
            return cartnZEsd;
        }

        /**
         * Sets the value of the cartnZEsd property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setCartnZEsd(JAXBElement<BigDecimal> value) {
            this.cartnZEsd = value;
        }

        /**
         * Gets the value of the atomTypeSymbol property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getAtomTypeSymbol() {
            return atomTypeSymbol;
        }

        /**
         * Sets the value of the atomTypeSymbol property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setAtomTypeSymbol(JAXBElement<String> value) {
            this.atomTypeSymbol = value;
        }

        /**
         * Gets the value of the bIso property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getBIso() {
            return bIso;
        }

        /**
         * Sets the value of the bIso property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setBIso(JAXBElement<BigDecimal> value) {
            this.bIso = value;
        }

        /**
         * Gets the value of the bIsoEsd property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getBIsoEsd() {
            return bIsoEsd;
        }

        /**
         * Sets the value of the bIsoEsd property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setBIsoEsd(JAXBElement<BigDecimal> value) {
            this.bIsoEsd = value;
        }

        /**
         * Gets the value of the fractX property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFractX() {
            return fractX;
        }

        /**
         * Sets the value of the fractX property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFractX(JAXBElement<BigDecimal> value) {
            this.fractX = value;
        }

        /**
         * Gets the value of the fractXEsd property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFractXEsd() {
            return fractXEsd;
        }

        /**
         * Sets the value of the fractXEsd property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFractXEsd(JAXBElement<BigDecimal> value) {
            this.fractXEsd = value;
        }

        /**
         * Gets the value of the fractY property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFractY() {
            return fractY;
        }

        /**
         * Sets the value of the fractY property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFractY(JAXBElement<BigDecimal> value) {
            this.fractY = value;
        }

        /**
         * Gets the value of the fractYEsd property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFractYEsd() {
            return fractYEsd;
        }

        /**
         * Sets the value of the fractYEsd property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFractYEsd(JAXBElement<BigDecimal> value) {
            this.fractYEsd = value;
        }

        /**
         * Gets the value of the fractZ property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFractZ() {
            return fractZ;
        }

        /**
         * Sets the value of the fractZ property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFractZ(JAXBElement<BigDecimal> value) {
            this.fractZ = value;
        }

        /**
         * Gets the value of the fractZEsd property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFractZEsd() {
            return fractZEsd;
        }

        /**
         * Sets the value of the fractZEsd property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFractZEsd(JAXBElement<BigDecimal> value) {
            this.fractZEsd = value;
        }

        /**
         * Gets the value of the occupancy property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getOccupancy() {
            return occupancy;
        }

        /**
         * Sets the value of the occupancy property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setOccupancy(JAXBElement<BigDecimal> value) {
            this.occupancy = value;
        }

        /**
         * Gets the value of the occupancyEsd property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getOccupancyEsd() {
            return occupancyEsd;
        }

        /**
         * Sets the value of the occupancyEsd property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setOccupancyEsd(JAXBElement<BigDecimal> value) {
            this.occupancyEsd = value;
        }

        /**
         * Gets the value of the occupancyIso property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getOccupancyIso() {
            return occupancyIso;
        }

        /**
         * Sets the value of the occupancyIso property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setOccupancyIso(JAXBElement<BigDecimal> value) {
            this.occupancyIso = value;
        }

        /**
         * Gets the value of the setId property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getSetId() {
            return setId;
        }

        /**
         * Sets the value of the setId property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setSetId(JAXBElement<String> value) {
            this.setId = value;
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
