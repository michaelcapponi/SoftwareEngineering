/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aaaws;

import javax.xml.ws.Endpoint;

/**
 *
 * @author biar
 */
public class AAAWSMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        
        String address = "http://localhost:8088/AAAWS";
        AAAWSImplementation impl = new AAAWSImplementation();
        Endpoint.publish(address, impl);
        System.out.println("Web service is running...");
        while(true){
            Thread.sleep(60*1000);
        }
    }
    
}
