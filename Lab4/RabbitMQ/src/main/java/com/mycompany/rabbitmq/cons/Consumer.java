/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rabbitmq.cons;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author biar
 */
public class Consumer {
    private final static String QUEUE_NAME = "Hello";
    
    public static void main(String[] args){
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("localhost");        //inserire corretto IP se richiesto
        
        try {
            Connection conn = cf.newConnection();
            Channel ch = conn.createChannel();
            ch.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println("[*] Waiting for messages. To exit press CTRL+C");
            DeliverCallback dc = (consumerTag, delivery) -> {
                String mess = new String(delivery.getBody(), "UTF-8");
                System.out.println("[x] Received '" + mess + "'");
            };
            ch.basicConsume(QUEUE_NAME, true, dc, consumerTag -> {});
        } catch (Exception ex) {
            Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
