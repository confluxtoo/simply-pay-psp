//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.10 at 11:53:53 AM IST 
//


package org.apache.fineract.infrastructure.upi.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="Head" type="{http://npci.org/upi/schema/}headType"/>
 *         &lt;element name="Txn" type="{http://npci.org/upi/schema/}payTrans"/>
 *         &lt;element name="Payees" type="{http://npci.org/upi/schema/}payeesType"/>
 *         &lt;element name="Payer" type="{http://npci.org/upi/schema/}payerType"/>
 *       &lt;/all>
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
@XmlRootElement(name = "ReqAuthDetails")
public class ReqAuthDetails {

    @XmlElement(name = "Head", required = true)
    protected HeadType head;
    @XmlElement(name = "Txn", required = true)
    protected PayTrans txn;
    @XmlElement(name = "Payees", required = true)
    protected PayeesType payees;
    @XmlElement(name = "Payer", required = true)
    protected PayerType payer;

    /**
     * Gets the value of the head property.
     * 
     * @return
     *     possible object is
     *     {@link HeadType }
     *     
     */
    public HeadType getHead() {
        return head;
    }

    /**
     * Sets the value of the head property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeadType }
     *     
     */
    public void setHead(HeadType value) {
        this.head = value;
    }

    /**
     * Gets the value of the txn property.
     * 
     * @return
     *     possible object is
     *     {@link PayTrans }
     *     
     */
    public PayTrans getTxn() {
        return txn;
    }

    /**
     * Sets the value of the txn property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayTrans }
     *     
     */
    public void setTxn(PayTrans value) {
        this.txn = value;
    }

    /**
     * Gets the value of the payees property.
     * 
     * @return
     *     possible object is
     *     {@link PayeesType }
     *     
     */
    public PayeesType getPayees() {
        return payees;
    }

    /**
     * Sets the value of the payees property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayeesType }
     *     
     */
    public void setPayees(PayeesType value) {
        this.payees = value;
    }

    /**
     * Gets the value of the payer property.
     * 
     * @return
     *     possible object is
     *     {@link PayerType }
     *     
     */
    public PayerType getPayer() {
        return payer;
    }

    /**
     * Sets the value of the payer property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayerType }
     *     
     */
    public void setPayer(PayerType value) {
        this.payer = value;
    }

}
