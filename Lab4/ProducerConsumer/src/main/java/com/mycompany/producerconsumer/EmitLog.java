/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.producerconsumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author biar
 */
public class EmitLog {
    
    private static final String EXCHANGE_NAME = "logs";
    
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");       //inserire corretto IP se richiesto
        //factory.setUsername("test");      //da inserire utente se richiesto
        //factory.setPassword("test");      //da inserire utente se richiesto
        try (Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()){
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            String message = args.length < 1 ? "info: Hello World!" : String.join(" ", args);
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent: " + message);
        }
    }
    
}
