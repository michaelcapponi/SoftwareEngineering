
package wdslbankaaaws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "BankAAAWS", targetNamespace = "http://bankaaaws/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface BankAAAWS {


    /**
     * 
     * @return
     *     returns java.util.List<wdslbankaaaws.Client>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getClients", targetNamespace = "http://bankaaaws/", className = "wdslbankaaaws.GetClients")
    @ResponseWrapper(localName = "getClientsResponse", targetNamespace = "http://bankaaaws/", className = "wdslbankaaaws.GetClientsResponse")
    public List<Client> getClients();

}
