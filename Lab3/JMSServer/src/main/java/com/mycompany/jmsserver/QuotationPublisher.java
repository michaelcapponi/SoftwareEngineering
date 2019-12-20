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
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author biar
 */
public class QuotationPublisher {
    private final String quotations[] = {"Telecom", "Finmeccanica", "Banca_Intesa", "Oracle", "Parmalat", "Mondadori", "Vodafone", "Barilla"};
    private Random random;
    private Properties props;
    private InitialContext intCont;
    private TopicConnectionFactory cf;
    private TopicConnection connection;
    private TopicSession session;
    private Topic destination;
    private TopicPublisher producer;

    public QuotationPublisher() {
        random = new Random();
        props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        intCont = null;
        cf = null;
        connection = null;
        session = null;
        destination = null;
        producer = null;
        try {
            intCont = new InitialContext(props);
            cf = (TopicConnectionFactory) intCont.lookup("ConnectionFactory");
        } catch (Exception ex) {
           ex.printStackTrace();
        } finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (JMSException ex) {
                    Logger.getLogger(QuotationPublisher.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void start() {
        try {
            connection = cf.createTopicConnection();
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = (Topic) intCont.lookup("dynamicTopics/Quotations");
            producer = session.createPublisher(destination);
            connection.start();
        } catch (Exception ex) {
            Logger.getLogger(QuotationPublisher.class.getName()).log(Level.SEVERE, null, ex);
        }
        TextMessage msg = null;
        while (true){
            try {
                session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
                msg = session.createTextMessage();
                msg.setStringProperty("Quotation", quotations[random.nextInt(this.quotations.length)]);
                msg.setDoubleProperty("Price", random.nextDouble() * this.quotations.length * 10);
                producer.send(msg);
                Thread.sleep(5000);                
            } catch (Exception ex) {
                Logger.getLogger(QuotationPublisher.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
