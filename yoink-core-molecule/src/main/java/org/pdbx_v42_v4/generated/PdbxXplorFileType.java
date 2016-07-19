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
 * 
 * Parameter and topology files used in X-PLOR/CNS refinement.
 * <PDBx:pdbx_xplor_fileCategory>
 *    <PDBx:pdbx_xplor_file pdbx_refine_id="x-ray" serial_no="1">
 *       <PDBx:param_file>parm_hol.dat</PDBx:param_file>
 *       <PDBx:topol_file>topol_hol.dat</PDBx:topol_file>
 *    </PDBx:pdbx_xplor_file>
 * </PDBx:pdbx_xplor_fileCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for pdbx_xplor_fileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pdbx_xplor_fileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pdbx_xplor_file" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="param_file" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="topol_file" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="pdbx_refine_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="serial_no" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "pdbx_xplor_fileType", propOrder = {
    "pdbxXplorFile"
})
public class PdbxXplorFileType {

    @XmlElement(name = "pdbx_xplor_file")
    protected List<PdbxXplorFileType.PdbxXplorFile> pdbxXplorFile;

    /**
     * Gets the value of the pdbxXplorFile property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pdbxXplorFile property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPdbxXplorFile().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PdbxXplorFileType.PdbxXplorFile }
     * 
     * 
     */
    public List<PdbxXplorFileType.PdbxXplorFile> getPdbxXplorFile() {
        if (pdbxXplorFile == null) {
            pdbxXplorFile = new ArrayList<PdbxXplorFileType.PdbxXplorFile>();
        }
        return this.pdbxXplorFile;
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
     *         &lt;element name="param_file" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="topol_file" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="pdbx_refine_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="serial_no" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class PdbxXplorFile {

        @XmlElementRef(name = "param_file", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> paramFile;
        @XmlElementRef(name = "topol_file", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> topolFile;
        @XmlAttribute(name = "pdbx_refine_id", required = true)
        protected String pdbxRefineId;
        @XmlAttribute(name = "serial_no", required = true)
        protected String serialNo;

        /**
         * Gets the value of the paramFile property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getParamFile() {
            return paramFile;
        }

        /**
         * Sets the value of the paramFile property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setParamFile(JAXBElement<String> value) {
            this.paramFile = value;
        }

        /**
         * Gets the value of the topolFile property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getTopolFile() {
            return topolFile;
        }

        /**
         * Sets the value of the topolFile property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setTopolFile(JAXBElement<String> value) {
            this.topolFile = value;
        }

        /**
         * Gets the value of the pdbxRefineId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPdbxRefineId() {
            return pdbxRefineId;
        }

        /**
         * Sets the value of the pdbxRefineId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPdbxRefineId(String value) {
            this.pdbxRefineId = value;
        }

        /**
         * Gets the value of the serialNo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSerialNo() {
            return serialNo;
        }

        /**
         * Sets the value of the serialNo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSerialNo(String value) {
            this.serialNo = value;
        }

    }

}
