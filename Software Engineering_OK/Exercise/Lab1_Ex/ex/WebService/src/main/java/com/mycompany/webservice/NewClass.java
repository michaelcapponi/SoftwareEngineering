/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webservice;

/**
 *
 * @author biar
 */
@javax.jws.WebService(endpointInterface="com.mycompany.webservice.NewInterfaccia")
public class NewClass implements NewInterfaccia {
    public String[] getClients(){
        String[] clients=new String[3];
        clients[0]="1,Gabriele";
        clients[1]="2,Fortunato";
        clients[2]="3,Silvio";
        return clients ;
    }
    
private static java.util.List<java.lang.String> getOperationsByClientID(int arg0) {
    it.sapienza.softeng.bankws.BankImplService service = new it.sapienza.softeng.bankws.BankImplService();
    it.sapienza.softeng.bankws.BankIFace port = service.getBankImplPort();
    return port.getOperationsByClientID(arg0);
}

}
