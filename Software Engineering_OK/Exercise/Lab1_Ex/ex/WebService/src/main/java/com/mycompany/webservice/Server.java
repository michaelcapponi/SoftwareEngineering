/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webservice;

import javax.xml.ws.Endpoint;


public class Server {
    public static void main(String args[])throws InterruptedException{
        NewClass classe=new NewClass();
        Endpoint.publish("http://localhost:8080/service",classe);
        Thread.sleep(1 * 1000);        
        
    }    
}
