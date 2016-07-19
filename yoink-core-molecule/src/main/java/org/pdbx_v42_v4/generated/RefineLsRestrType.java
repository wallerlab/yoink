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
 * Data items in the REFINE_LS_RESTR category record details about
 * the restraints applied to various classes of parameters during
 * the least-squares refinement.
 * 
 *     Example 1 - based on PDB entry 5HVP and laboratory records for the
 *                 structure corresponding to PDB entry 5HVP.
 * <PDBx:refine_ls_restrCategory>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="bond_d">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>0.018</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>0.020</PDBx:dev_ideal_target>
 *       <PDBx:number>1654</PDBx:number>
 *       <PDBx:rejects>22</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="angle_d">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>0.038</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>0.030</PDBx:dev_ideal_target>
 *       <PDBx:number>2246</PDBx:number>
 *       <PDBx:rejects>139</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="planar_d">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>0.043</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>0.040</PDBx:dev_ideal_target>
 *       <PDBx:number>498</PDBx:number>
 *       <PDBx:rejects>21</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="planar">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>0.015</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>0.020</PDBx:dev_ideal_target>
 *       <PDBx:number>270</PDBx:number>
 *       <PDBx:rejects>1</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="chiral">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>0.177</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>0.150</PDBx:dev_ideal_target>
 *       <PDBx:number>278</PDBx:number>
 *       <PDBx:rejects>2</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="singtor_nbd">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>0.216</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>0.500</PDBx:dev_ideal_target>
 *       <PDBx:number>582</PDBx:number>
 *       <PDBx:rejects>0</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="multtor_nbd">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>0.207</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>0.500</PDBx:dev_ideal_target>
 *       <PDBx:number>419</PDBx:number>
 *       <PDBx:rejects>0</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="xyhbond_nbd">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>0.245</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>0.500</PDBx:dev_ideal_target>
 *       <PDBx:number>149</PDBx:number>
 *       <PDBx:rejects>0</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="planar_tor">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>2.6</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>3.0</PDBx:dev_ideal_target>
 *       <PDBx:number>203</PDBx:number>
 *       <PDBx:rejects>9</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="staggered_tor">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>17.4</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>15.0</PDBx:dev_ideal_target>
 *       <PDBx:number>298</PDBx:number>
 *       <PDBx:rejects>31</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 *    <PDBx:refine_ls_restr pdbx_refine_id="X-ray" type="orthonormal_tor">
 *       <PDBx:criterion>&gt; 2\s</PDBx:criterion>
 *       <PDBx:dev_ideal>18.1</PDBx:dev_ideal>
 *       <PDBx:dev_ideal_target>20.0</PDBx:dev_ideal_target>
 *       <PDBx:number>12</PDBx:number>
 *       <PDBx:rejects>1</PDBx:rejects>
 *    </PDBx:refine_ls_restr>
 * </PDBx:refine_ls_restrCategory>
 * 
 * 
 *          
 * 
 * <p>Java class for refine_ls_restrType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="refine_ls_restrType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="refine_ls_restr" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;all>
 *                   &lt;element name="criterion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="dev_ideal" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0.0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="dev_ideal_target" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0.0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="number" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="pdbx_restraint_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="rejects" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *                 &lt;/all>
 *                 &lt;attribute name="pdbx_refine_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "refine_ls_restrType", propOrder = {
    "refineLsRestr"
})
public class RefineLsRestrType {

    @XmlElement(name = "refine_ls_restr")
    protected List<RefineLsRestrType.RefineLsRestr> refineLsRestr;

    /**
     * Gets the value of the refineLsRestr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the refineLsRestr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRefineLsRestr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RefineLsRestrType.RefineLsRestr }
     * 
     * 
     */
    public List<RefineLsRestrType.RefineLsRestr> getRefineLsRestr() {
        if (refineLsRestr == null) {
            refineLsRestr = new ArrayList<RefineLsRestrType.RefineLsRestr>();
        }
        return this.refineLsRestr;
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
     *         &lt;element name="criterion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="dev_ideal" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0.0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="dev_ideal_target" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0.0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="number" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="pdbx_restraint_function" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="rejects" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}integer">
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="weight" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
     *       &lt;/all>
     *       &lt;attribute name="pdbx_refine_id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
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
    public static class RefineLsRestr {

        @XmlElementRef(name = "criterion", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> criterion;
        @XmlElementRef(name = "dev_ideal", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> devIdeal;
        @XmlElementRef(name = "dev_ideal_target", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> devIdealTarget;
        @XmlElementRef(name = "number", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigInteger> number;
        @XmlElementRef(name = "pdbx_restraint_function", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<String> pdbxRestraintFunction;
        @XmlElementRef(name = "rejects", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigInteger> rejects;
        @XmlElementRef(name = "weight", namespace = "http://pdbml.pdb.org/schema/pdbx-v42.xsd", type = JAXBElement.class, required = false)
        protected JAXBElement<BigDecimal> weight;
        @XmlAttribute(name = "pdbx_refine_id", required = true)
        protected String pdbxRefineId;
        @XmlAttribute(name = "type", required = true)
        protected String type;

        /**
         * Gets the value of the criterion property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getCriterion() {
            return criterion;
        }

        /**
         * Sets the value of the criterion property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setCriterion(JAXBElement<String> value) {
            this.criterion = value;
        }

        /**
         * Gets the value of the devIdeal property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getDevIdeal() {
            return devIdeal;
        }

        /**
         * Sets the value of the devIdeal property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setDevIdeal(JAXBElement<BigDecimal> value) {
            this.devIdeal = value;
        }

        /**
         * Gets the value of the devIdealTarget property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getDevIdealTarget() {
            return devIdealTarget;
        }

        /**
         * Sets the value of the devIdealTarget property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setDevIdealTarget(JAXBElement<BigDecimal> value) {
            this.devIdealTarget = value;
        }

        /**
         * Gets the value of the number property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public JAXBElement<BigInteger> getNumber() {
            return number;
        }

        /**
         * Sets the value of the number property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public void setNumber(JAXBElement<BigInteger> value) {
            this.number = value;
        }

        /**
         * Gets the value of the pdbxRestraintFunction property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public JAXBElement<String> getPdbxRestraintFunction() {
            return pdbxRestraintFunction;
        }

        /**
         * Sets the value of the pdbxRestraintFunction property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link String }{@code >}
         *     
         */
        public void setPdbxRestraintFunction(JAXBElement<String> value) {
            this.pdbxRestraintFunction = value;
        }

        /**
         * Gets the value of the rejects property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public JAXBElement<BigInteger> getRejects() {
            return rejects;
        }

        /**
         * Sets the value of the rejects property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigInteger }{@code >}
         *     
         */
        public void setRejects(JAXBElement<BigInteger> value) {
            this.rejects = value;
        }

        /**
         * Gets the value of the weight property.
         * 
         * @return
         *     possible object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public JAXBElement<BigDecimal> getWeight() {
            return weight;
        }

        /**
         * Sets the value of the weight property.
         * 
         * @param value
         *     allowed object is
         *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
         *     
         */
        public void setWeight(JAXBElement<BigDecimal> value) {
            this.weight = value;
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
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

    }

}
