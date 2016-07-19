//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.22 at 05:23:04 PM EST 
//


package org.gradle.pdbml.v40.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 * Data items in the AUDIT_LINK category record details about the
 * relationships between data blocks in the current CIF.
 * 
 *     Example 1 - multiple structure paper, as illustrated
 *                 in A Guide to CIF for Authors (1995). IUCr: Chester.
 * <PDBx:audit_linkCategory>
 *    <PDBx:audit_link block_code="morA_pub" block_description="discursive text of paper with two structures"></PDBx:audit_link>
 *    <PDBx:audit_link block_code="morA_(1)" block_description="structure 1 of 2"></PDBx:audit_link>
 *    <PDBx:audit_link block_code="morA_(2)" block_description="structure 2 of 2"></PDBx:audit_link>
 * </PDBx:audit_linkCategory>
 * 
 * 
 *     Example 2 - example file for the one-dimensional incommensurately
 *                 modulated structure of K~2~SeO~4~.
 * <PDBx:audit_linkCategory>
 *    <PDBx:audit_link block_code="KSE_PUB" block_description="publication details"></PDBx:audit_link>
 *    <PDBx:audit_link block_code="KSE_COM" block_description="experimental data common to ref./mod. structures"></PDBx:audit_link>
 *    <PDBx:audit_link block_code="KSE_REF" block_description="reference structure"></PDBx:audit_link>
 *    <PDBx:audit_link block_code="KSE_MOD" block_description="modulated structure"></PDBx:audit_link>
 * </PDBx:audit_linkCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for audit_linkType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="audit_linkType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="audit_link" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="block_code" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="block_description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "audit_linkType", propOrder = {
    "auditLink"
})
public class AuditLinkType {

    @XmlElement(name = "audit_link")
    protected List<AuditLinkType.AuditLink> auditLink;

    /**
     * Gets the value of the auditLink property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the auditLink property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuditLink().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AuditLinkType.AuditLink }
     * 
     * 
     */
    public List<AuditLinkType.AuditLink> getAuditLink() {
        if (auditLink == null) {
            auditLink = new ArrayList<AuditLinkType.AuditLink>();
        }
        return this.auditLink;
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
     *       &lt;attribute name="block_code" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="block_description" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AuditLink {

        @XmlAttribute(name = "block_code", required = true)
        protected String blockCode;
        @XmlAttribute(name = "block_description", required = true)
        protected String blockDescription;

        /**
         * Gets the value of the blockCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBlockCode() {
            return blockCode;
        }

        /**
         * Sets the value of the blockCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBlockCode(String value) {
            this.blockCode = value;
        }

        /**
         * Gets the value of the blockDescription property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBlockDescription() {
            return blockDescription;
        }

        /**
         * Sets the value of the blockDescription property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBlockDescription(String value) {
            this.blockDescription = value;
        }

    }

}
