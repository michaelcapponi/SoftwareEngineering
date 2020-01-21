/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userapplication;

import java.util.List;

/**
 *
 * @author biar
 */
public class UserApplication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int c= 1;
        String res = "pippo";
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
        System.out.println("FORTNITE > JAVA");
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
    
}
