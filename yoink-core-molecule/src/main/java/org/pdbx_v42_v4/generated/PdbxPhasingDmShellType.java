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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Data items in the PDBX_PHASING_DM_SHELL category record details about 
 * density modification in resolution shell.
 * 
 *     Example 1 - density modification with shells
 * <PDBx:pdbx_phasing_dm_shellCategory>
 *    <PDBx:pdbx_phasing_dm_shell d_res_high="7.73" d_res_low="100.00">
 *       <PDBx:delta_phi_final>24.7</PDBx:delta_phi_final>
 *       <PDBx:fom>0.879</PDBx:fom>
 *       <PDBx:reflns>502</PDBx:reflns>
 *    </PDBx:pdbx_phasing_dm_shell>
 *    <PDBx:pdbx_phasing_dm_shell d_res_high="6.24" d_res_low="7.73">
 *       <PDBx:delta_phi_final>29.2</PDBx:delta_phi_final>
 *       <PDBx:fom>0.857</PDBx:fom>
 *       <PDBx:reflns>506</PDBx:reflns>
 *    </PDBx:pdbx_phasing_dm_shell>
 *    <PDBx:pdbx_phasing_dm_shell d_res_high="5.50" d_res_low="6.24">
 *       <PDBx:delta_phi_final>29.2</PDBx:delta_phi_final>
 *       <PDBx:fom>0.838</PDBx:fom>
 *       <PDBx:reflns>504</PDBx:reflns>
 *    </PDBx:pdbx_phasing_dm_shell>
 *    <PDBx:pdbx_phasing_dm_shell d_res_high="5.02" d_res_low="5.50">
 *       <PDBx:delta_phi_final>25.3</PDBx:delta_phi_final>
 *       <PDBx:fom>0.851</PDBx:fom>
 *       <PDBx:reflns>502</PDBx:reflns>
 *    </PDBx:pdbx_phasing_dm_shell>
 *    <PDBx:pdbx_phasing_dm_shell d_res_high="4.67" d_res_low="5.02">
 *       <PDBx:delta_phi_final>22.7</PDBx:delta_phi_final>
 *       <PDBx:fom>0.831</PDBx:fom>
 *       <PDBx:reflns>503</PDBx:reflns>
 *    </PDBx:pdbx_phasing_dm_shell>
 * </PDBx:pdbx_phasing_dm_shellCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_phasing_dm_shellType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_phasing_dm_shellType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_phasing_dm_shell" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="delta_phi_final" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="delta_phi_initial" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fom" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fom_acentric" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="fom_centric" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                   &lt;element name="reflns" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="reflns_acentric" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                   &lt;element name="reflns_centric" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="d_res_high" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *                 &lt;attribute name="d_res_low" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
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
@XmlType(name = "pdbx_phasing_dm_shellType", propOrder = {
    "pdbxPhasingDmShell"
})
public class PdbxPhasingDmShellType {

    @XmlElement(name = "pdbx_phasing_dm_shell")
    protected List<PdbxPhasingDmShellType.PdbxPhasingDmShell> pdbxPhasingDmShell;

    /**
     * Gets the value of the pdbxPhasingDmShell property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxPhasingDmShell property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxPhasingDmShell().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxPhasingDmShellType.PdbxPhasingDmShell }
     * 
     * 
     */
    public List<PdbxPhasingDmShellType.PdbxPhasingDmShell> getPdbxPhasingDmShell() {
        if (pdbxPhasingDmShell == null) {
            pdbxPhasingDmShell = new ArrayList<PdbxPhasingDmShellType.PdbxPhasingDmShell>();
        }
        return this.pdbxPhasingDmShell;
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
     *         &lt;element name="delta_phi_final" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="delta_phi_initial" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fom" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fom_acentric" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="fom_centric" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *         &lt;element name="reflns" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="reflns_acentric" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *         &lt;element name="reflns_centric" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="d_res_high" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
     *       &lt;attribute name="d_res_low" use="required" type="{http://www.w3.org/2001/XMLSchema}decimal" />
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
    public static class PdbxPhasingDmShell {

        @XmlElementRef(name = "delta_phi_final", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> deltaPhiFinal;
        @XmlElementRef(name = "delta_phi_initial", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> deltaPhiInitial;
        @XmlElementRef(name = "fom", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fom;
        @XmlElementRef(name = "fom_acentric", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fomAcentric;
        @XmlElementRef(name = "fom_centric", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> fomCentric;
        @XmlElementRef(name = "reflns", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigInteger> reflns;
        @XmlElementRef(name = "reflns_acentric", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigInteger> reflnsAcentric;
        @XmlElementRef(name = "reflns_centric", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigInteger> reflnsCentric;
        @XmlAttribute(name = "d_res_high", required = true)
        protected BigDecimal dResHigh;
        @XmlAttribute(name = "d_res_low", required = true)
        protected BigDecimal dResLow;

        /**
         * Gets the value of the deltaPhiFinal property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getDeltaPhiFinal() {
            return deltaPhiFinal;
        }

        /**
         * Sets the value of the deltaPhiFinal property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setDeltaPhiFinal(JAXBElement<BigDecimal> value) {
            this.deltaPhiFinal = value;
        }

        /**
         * Gets the value of the deltaPhiInitial property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getDeltaPhiInitial() {
            return deltaPhiInitial;
        }

        /**
         * Sets the value of the deltaPhiInitial property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setDeltaPhiInitial(JAXBElement<BigDecimal> value) {
            this.deltaPhiInitial = value;
        }

        /**
         * Gets the value of the fom property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFom() {
            return fom;
        }

        /**
         * Sets the value of the fom property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFom(JAXBElement<BigDecimal> value) {
            this.fom = value;
        }

        /**
         * Gets the value of the fomAcentric property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFomAcentric() {
            return fomAcentric;
        }

        /**
         * Sets the value of the fomAcentric property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFomAcentric(JAXBElement<BigDecimal> value) {
            this.fomAcentric = value;
        }

        /**
         * Gets the value of the fomCentric property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getFomCentric() {
            return fomCentric;
        }

        /**
         * Sets the value of the fomCentric property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setFomCentric(JAXBElement<BigDecimal> value) {
            this.fomCentric = value;
        }

        /**
         * Gets the value of the reflns property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public JAXBElement<BigInteger> getReflns() {
            return reflns;
        }

        /**
         * Sets the value of the reflns property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public void setReflns(JAXBElement<BigInteger> value) {
            this.reflns = value;
        }

        /**
         * Gets the value of the reflnsAcentric property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public JAXBElement<BigInteger> getReflnsAcentric() {
            return reflnsAcentric;
        }

        /**
         * Sets the value of the reflnsAcentric property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public void setReflnsAcentric(JAXBElement<BigInteger> value) {
            this.reflnsAcentric = value;
        }

        /**
         * Gets the value of the reflnsCentric property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public JAXBElement<BigInteger> getReflnsCentric() {
            return reflnsCentric;
        }

        /**
         * Sets the value of the reflnsCentric property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public void setReflnsCentric(JAXBElement<BigInteger> value) {
            this.reflnsCentric = value;
        }

        /**
         * Gets the value of the dResHigh property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getDResHigh() {
            return dResHigh;
        }

        /**
         * Sets the value of the dResHigh property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setDResHigh(BigDecimal value) {
            this.dResHigh = value;
        }

        /**
         * Gets the value of the dResLow property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getDResLow() {
            return dResLow;
        }

        /**
         * Sets the value of the dResLow property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setDResLow(BigDecimal value) {
            this.dResLow = value;
        }

    }

}
