
package wdslbankinterface;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for operation complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="operation">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Operation_Code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Client_Code" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Amount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "operation", propOrder = {
    "operationCode",
    "clientCode",
    "amount",
    "description"
})
public class Operation {

    @XmlElement(name = "Operation_Code")
    protected int operationCode;
    @XmlElement(name = "Client_Code")
    protected int clientCode;
    @XmlElement(name = "Amount")
    protected int amount;
    @XmlElement(name = "Description")
    protected String description;

    /**
     * Gets the value of the operationCode property.
     * 
     */
    public int getOperationCode() {
        return operationCode;
    }

    /**
     * Sets the value of the operationCode property.
     * 
     */
    public void setOperationCode(int value) {
        this.operationCode = value;
    }

    /**
     * Gets the value of the clientCode property.
     * 
     */
    public int getClientCode() {
        return clientCode;
    }

    /**
     * Sets the value of the clientCode property.
     * 
     */
    public void setClientCode(int value) {
        this.clientCode = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(int value) {
        this.amount = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
