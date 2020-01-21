/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortunato.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author biar
 */
public class Send {
    private final static String QUEUE_NAME = "Hello";
    
    public static void main(String[] args){
        ConnectionFactory cf = new ConnectionFactory();
        cf.setHost("192.168.0.16");
        cf.setUsername("test");
        cf.setPassword("test");
        try {
            Connection conn = cf.newConnection();
            Channel ch = conn.createChannel();
            ch.queueDeclare(QUEUE_NAME, false, false, false, null);
            String mess = "Hello World!";
            ch.basicPublish("", QUEUE_NAME, null, mess.getBytes());
            System.out.println(" [x] Sent '" + mess + "'");
        } catch (Exception ex) {
            Logger.getLogger(Send.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
