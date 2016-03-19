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
 *       &lt;sequence>
 *         &lt;element name="Head" type="{http://npci.org/upi/schema/}headType"/>
 *         &lt;element name="Resp" type="{http://npci.org/upi/schema/}respType"/>
 *         &lt;element name="Txn" type="{http://npci.org/upi/schema/}payTrans"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "head",
    "resp",
    "txn"
})
@XmlRootElement(name = "RespHbt")
public class RespHbt {

    @XmlElement(name = "Head", required = true)
    protected HeadType head;
    @XmlElement(name = "Resp", required = true)
    protected RespType resp;
    @XmlElement(name = "Txn", required = true)
    protected PayTrans txn;

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
     * Gets the value of the resp property.
     * 
     * @return
     *     possible object is
     *     {@link RespType }
     *     
     */
    public RespType getResp() {
        return resp;
    }

    /**
     * Sets the value of the resp property.
     * 
     * @param value
     *     allowed object is
     *     {@link RespType }
     *     
     */
    public void setResp(RespType value) {
        this.resp = value;
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

}
