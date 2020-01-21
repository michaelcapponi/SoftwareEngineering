/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientbankfortunato;

import bankws.Operation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author biar
 */
public class ClientBankFortunato {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String toCheck = "Benzina autostrada";
        List<String> result = new ArrayList<>();
        List<String> clients = getClients();
        Iterator<String> itClients = clients.iterator();
        
        while(itClients.hasNext()){
            String[] clientInfos = itClients.next().split(",");
            List<Integer> operations = getOperationsByClientID(Integer.parseInt(clientInfos[0]));
            Operation op = getOperationDetailsByID(Integer.parseInt(clientInfos[0]));
            if(op.getDescription().equalsIgnoreCase(toCheck)){
                result.add(clientInfos[1]);
            }
        }
        
        System.out.println(result.toString());
        
    }

    private static java.util.List<java.lang.String> getClients() {
        aaaws.AAAWSImplService service = new aaaws.AAAWSImplService();
        aaaws.AAAWS port = service.getAAAWSImplPort();
        return port.getClients();
    }

    private static Operation getOperationDetailsByID(int arg0) {
        bankws.BankImplService service = new bankws.BankImplService();
        bankws.BankIFace port = service.getBankImplPort();
        return port.getOperationDetailsByID(arg0);
    }

    private static java.util.List<java.lang.Integer> getOperationsByClientID(int arg0) {
        bankws.BankImplService service = new bankws.BankImplService();
        bankws.BankIFace port = service.getBankImplPort();
        return port.getOperationsByClientID(arg0);
    }
    
}
