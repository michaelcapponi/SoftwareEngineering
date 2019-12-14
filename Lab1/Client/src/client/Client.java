/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import bankws.Operation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author biar
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String description = "benzina autostrada";
        List<String> result = new ArrayList<>();
        List<String> clients = getClients();
        Iterator<String> cl_iterator = clients.iterator();
        
        while(cl_iterator.hasNext()){
            String[] clientInfos = cl_iterator.next().split(",");
            List<Integer> operations = getOperationsByClientID(Integer.parseInt(clientInfos[0]));
            Operation op = getOperationDetailsByID(Integer.parseInt(clientInfos[0]));
            if(op.getDescription().equals(description)){
                result.add(clientInfos[1]);
            }
        }
        
        System.out.println(result.toString());
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

    private static java.util.List<java.lang.String> getClients() {
        aaaws.AAAWSImplementationService service = new aaaws.AAAWSImplementationService();
        aaaws.AAAWS port = service.getAAAWSImplementationPort();
        return port.getClients();
    }
    
}
