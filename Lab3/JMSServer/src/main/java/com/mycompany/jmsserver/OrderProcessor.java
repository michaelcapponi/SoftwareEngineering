/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsserver;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author biar
 */
public class OrderProcessor implements MessageListener{
    private Random random;
    private Properties props;
    private InitialContext intCont;
    private TopicConnectionFactory cf;
    private TopicConnection connection;
    private TopicSession session;
    private Topic destination, destination1;
    private TopicSubscriber subscriber;
    private TopicPublisher producer;
    
    public OrderProcessor(){
        random = new Random();
        props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        try {
            intCont = new InitialContext(props);
            cf = (TopicConnectionFactory) intCont.lookup("ConnectionFactory");
            connection = cf.createTopicConnection();
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = (Topic) intCont.lookup("dynamicTopics/Orders");
            producer = session.createPublisher(destination);
            subscriber = session.createSubscriber(destination);
            subscriber.setMessageListener(this);
            connection.start();
        } catch (Exception ex) {
            Logger.getLogger(OrderProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onMessage(Message msg) {
        System.out.println("Order received");
        TextMessage mess = (TextMessage) msg;
        try {
            TextMessage reply = session.createTextMessage();
            reply.setStringProperty("User", mess.getStringProperty("User"));
            reply.setStringProperty("Quotation", mess.getStringProperty("Quotation"));
            reply.setIntProperty("Amount", mess.getIntProperty("Amount"));
            reply.setDoubleProperty("Price", mess.getDoubleProperty("Price"));
            reply.setBooleanProperty("Status", random.nextFloat() < 0.5);
            producer.send(reply);
            System.out.println("Send response");
        } catch (Exception ex) {
            Logger.getLogger(OrderProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
