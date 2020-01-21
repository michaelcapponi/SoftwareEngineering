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
    public static void main(String[] args) throws InterruptedException{
        String address = "http://localhost:8088/AAAWS";
        AAAWSImpl impl = new AAAWSImpl();
        Endpoint.publish(address, impl);
        while(true){
            Thread.sleep(60*1000);
        }
    }
}
