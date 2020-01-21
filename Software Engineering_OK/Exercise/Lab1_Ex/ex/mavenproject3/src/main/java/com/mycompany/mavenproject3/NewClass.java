package com.mycompany.mavenproject3;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;

/**
 *
 * @author biar
 */
public class NewClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int c= 1;
        String res = "";
        while(c != 0){
            res = getOperationDetailsByID(c);
            if(res == null){
                c = 0;
            }
            else{
                System.out.println(res);
                c++;
            }
        }
        c = 1;
        System.out.println("Next Operation");
        while(c != 0){
            List<String> res1 = getOperationsByClientID(c);
            if(res1.isEmpty()){
                c = 0;
            }
            else{
                System.out.println(res1);
                c++;
            }
        }
        List<String> res2 = getClients();
        System.out.println(res2);
        
        
    }

    
    private static String getOperationDetailsByID(int arg0) {
        it.sapienza.softeng.bankws.BankImplService service = new it.sapienza.softeng.bankws.BankImplService();
        it.sapienza.softeng.bankws.BankIFace port = service.getBankImplPort();
        return port.getOperationDetailsByID(arg0);
    }

    private static java.util.List<java.lang.String> getOperationsByClientID(int arg0) {
        it.sapienza.softeng.bankws.BankImplService service = new it.sapienza.softeng.bankws.BankImplService();
        it.sapienza.softeng.bankws.BankIFace port = service.getBankImplPort();
        return port.getOperationsByClientID(arg0);
    }
    
    
    private static java.util.List<java.lang.String> getClients() {
        com.mycompany.webservice.NewClassService service = new com.mycompany.webservice.NewClassService();
        com.mycompany.webservice.NewInterfaccia port = service.getNewClassPort();
        java.util.List<java.lang.String> result = port.getClients();
        return result;
    }



}


