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
import javax.xml.bind.annotation.XmlValue;


/**
 * 
 * Data items in the EXPTL_CRYSTAL_FACE category record details
 * of the crystal faces.
 * 
 *     Example 1 - based on laboratory records for Yb(S-C5H4N)2(THF)4
 *                 for the 100 face of crystal xstl1.
 * <PDBx:exptl_crystal_faceCategory>
 *    <PDBx:exptl_crystal_face crystal_id="xstl1" index_h="1" index_k="0" index_l="0">
 *       <PDBx:diffr_chi>42.56</PDBx:diffr_chi>
 *       <PDBx:diffr_kappa>30.23</PDBx:diffr_kappa>
 *       <PDBx:diffr_phi>-125.56</PDBx:diffr_phi>
 *       <PDBx:diffr_psi>-0.34</PDBx:diffr_psi>
 *       <PDBx:perp_dist>0.025</PDBx:perp_dist>
 *    </PDBx:exptl_crystal_face>
 * </PDBx:exptl_crystal_faceCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for exptl_crystal_faceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exptl_crystal_faceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="exptl_crystal_face" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="diffr_chi" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
 *                           &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="diffr_kappa" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
 *                           &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="diffr_phi" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
 *                           &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="diffr_psi" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
 *                           &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="perp_dist" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;union>
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                             &lt;minExclusive value="0.0"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                             &lt;minInclusive value="0.0"/>
 *                             &lt;maxInclusive value="0.0"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/union>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/all>
 *                 &lt;attribute name="crystal_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="index_h" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="index_k" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *                 &lt;attribute name="index_l" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
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
@XmlType(name = "exptl_crystal_faceType", propOrder = {
    "exptlCrystalFace"
})
public class ExptlCrystalFaceType {

    @XmlElement(name = "exptl_crystal_face")
    protected List<ExptlCrystalFaceType.ExptlCrystalFace> exptlCrystalFace;

    /**
     * Gets the value of the exptlCrystalFace property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the exptlCrystalFace property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExptlCrystalFace().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExptlCrystalFaceType.ExptlCrystalFace }
     * 
     * 
     */
    public List<ExptlCrystalFaceType.ExptlCrystalFace> getExptlCrystalFace() {
        if (exptlCrystalFace == null) {
            exptlCrystalFace = new ArrayList<ExptlCrystalFaceType.ExptlCrystalFace>();
        }
        return this.exptlCrystalFace;
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
     *         &lt;element name="diffr_chi" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
     *                 &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="diffr_kappa" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
     *                 &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="diffr_phi" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
     *                 &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="diffr_psi" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
     *                 &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="perp_dist" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;union>
     *               &lt;simpleType>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                   &lt;minExclusive value="0.0"/>
     *                 &lt;/restriction>
     *               &lt;/simpleType>
     *               &lt;simpleType>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                   &lt;minInclusive value="0.0"/>
     *                   &lt;maxInclusive value="0.0"/>
     *                 &lt;/restriction>
     *               &lt;/simpleType>
     *             &lt;/union>
     *           &lt;/simpleType>
     *         &lt;/element>
     *       &lt;/all>
     *       &lt;attribute name="crystal_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="index_h" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="index_k" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
     *       &lt;attribute name="index_l" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
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
    public static class ExptlCrystalFace {

        @XmlElementRef(name = "diffr_chi", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrChi> diffrChi;
        @XmlElementRef(name = "diffr_kappa", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrKappa> diffrKappa;
        @XmlElementRef(name = "diffr_phi", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrPhi> diffrPhi;
        @XmlElementRef(name = "diffr_psi", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrPsi> diffrPsi;
        @XmlElementRef(name = "perp_dist", namespace = "http://pdbml.pdb.org/schema/pdbx-v40.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> perpDist;
        @XmlAttribute(name = "crystal_id", required = true)
        protected String crystalId;
        @XmlAttribute(name = "index_h", required = true)
        protected BigInteger indexH;
        @XmlAttribute(name = "index_k", required = true)
        protected BigInteger indexK;
        @XmlAttribute(name = "index_l", required = true)
        protected BigInteger indexL;

        /**
         * Gets the value of the diffrChi property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link ExptlCrystalFaceType.ExptlCrystalFace.DiffrChi }{@code >}
         *     
         */
        public JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrChi> getDiffrChi() {
            return diffrChi;
        }

        /**
         * Sets the value of the diffrChi property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link ExptlCrystalFaceType.ExptlCrystalFace.DiffrChi }{@code >}
         *     
         */
        public void setDiffrChi(JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrChi> value) {
            this.diffrChi = value;
        }

        /**
         * Gets the value of the diffrKappa property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link ExptlCrystalFaceType.ExptlCrystalFace.DiffrKappa }{@code >}
         *     
         */
        public JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrKappa> getDiffrKappa() {
            return diffrKappa;
        }

        /**
         * Sets the value of the diffrKappa property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link ExptlCrystalFaceType.ExptlCrystalFace.DiffrKappa }{@code >}
         *     
         */
        public void setDiffrKappa(JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrKappa> value) {
            this.diffrKappa = value;
        }

        /**
         * Gets the value of the diffrPhi property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link ExptlCrystalFaceType.ExptlCrystalFace.DiffrPhi }{@code >}
         *     
         */
        public JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrPhi> getDiffrPhi() {
            return diffrPhi;
        }

        /**
         * Sets the value of the diffrPhi property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link ExptlCrystalFaceType.ExptlCrystalFace.DiffrPhi }{@code >}
         *     
         */
        public void setDiffrPhi(JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrPhi> value) {
            this.diffrPhi = value;
        }

        /**
         * Gets the value of the diffrPsi property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link ExptlCrystalFaceType.ExptlCrystalFace.DiffrPsi }{@code >}
         *     
         */
        public JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrPsi> getDiffrPsi() {
            return diffrPsi;
        }

        /**
         * Sets the value of the diffrPsi property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link ExptlCrystalFaceType.ExptlCrystalFace.DiffrPsi }{@code >}
         *     
         */
        public void setDiffrPsi(JAXBElement<ExptlCrystalFaceType.ExptlCrystalFace.DiffrPsi> value) {
            this.diffrPsi = value;
        }

        /**
         * Gets the value of the perpDist property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPerpDist() {
            return perpDist;
        }

        /**
         * Sets the value of the perpDist property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPerpDist(JAXBElement<String> value) {
            this.perpDist = value;
        }

        /**
         * Gets the value of the crystalId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCrystalId() {
            return crystalId;
        }

        /**
         * Sets the value of the crystalId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCrystalId(String value) {
            this.crystalId = value;
        }

        /**
         * Gets the value of the indexH property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIndexH() {
            return indexH;
        }

        /**
         * Sets the value of the indexH property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIndexH(BigInteger value) {
            this.indexH = value;
        }

        /**
         * Gets the value of the indexK property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIndexK() {
            return indexK;
        }

        /**
         * Sets the value of the indexK property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIndexK(BigInteger value) {
            this.indexK = value;
        }

        /**
         * Gets the value of the indexL property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getIndexL() {
            return indexL;
        }

        /**
         * Sets the value of the indexL property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setIndexL(BigInteger value) {
            this.indexL = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
         *       &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class DiffrChi {

            @XmlValue
            protected BigDecimal value;
            @XmlAttribute(name = "units")
            protected String units;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValue(BigDecimal value) {
                this.value = value;
            }

            /**
             * Gets the value of the units property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnits() {
                if (units == null) {
                    return "degrees";
                } else {
                    return units;
                }
            }

            /**
             * Sets the value of the units property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnits(String value) {
                this.units = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
         *       &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class DiffrKappa {

            @XmlValue
            protected BigDecimal value;
            @XmlAttribute(name = "units")
            protected String units;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValue(BigDecimal value) {
                this.value = value;
            }

            /**
             * Gets the value of the units property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnits() {
                if (units == null) {
                    return "degrees";
                } else {
                    return units;
                }
            }

            /**
             * Sets the value of the units property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnits(String value) {
                this.units = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
         *       &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class DiffrPhi {

            @XmlValue
            protected BigDecimal value;
            @XmlAttribute(name = "units")
            protected String units;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValue(BigDecimal value) {
                this.value = value;
            }

            /**
             * Gets the value of the units property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnits() {
                if (units == null) {
                    return "degrees";
                } else {
                    return units;
                }
            }

            /**
             * Sets the value of the units property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnits(String value) {
                this.units = value;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;simpleContent>
         *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>decimal">
         *       &lt;attribute name="units" type="{http://www.w3.org/2001/XMLSchema}string" fixed="degrees" />
         *     &lt;/extension>
         *   &lt;/simpleContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class DiffrPsi {

            @XmlValue
            protected BigDecimal value;
            @XmlAttribute(name = "units")
            protected String units;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setValue(BigDecimal value) {
                this.value = value;
            }

            /**
             * Gets the value of the units property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnits() {
                if (units == null) {
                    return "degrees";
                } else {
                    return units;
                }
            }

            /**
             * Sets the value of the units property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnits(String value) {
                this.units = value;
            }

        }

    }

}
