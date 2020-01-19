/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client;

import java.util.Enumeration;
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
public class ProfessorManager implements MessageListener{
    private Properties props;
    private InitialContext intCont;
    private TopicConnectionFactory cf;
    private TopicConnection connection;
    private TopicSession session;
    private Topic destination;
    private TopicPublisher producer;
    private TopicSubscriber subscriber;       //order and quotation subscribers

    public ProfessorManager() {
        props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        try {
            intCont = new InitialContext(props);
            cf = (TopicConnectionFactory) intCont.lookup("ConnectionFactory");
            destination = (Topic) intCont.lookup("dynamicTopics/professors");
            connection = cf.createTopicConnection();
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            subscriber = session.createSubscriber(destination);
            subscriber.setMessageListener(this);
            connection.start();
        } catch (Exception ex) {
            Logger.getLogger(ProfessorManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onMessage(Message msg) {
        TextMessage mess = (TextMessage) msg;
        try {
            float rank = mess.getFloatProperty("rank");
            String id = mess.getStringProperty("id");
            try { // Call Web Service Operation
                edu.uniroma1.msecs.soapserver.ExamImplService service = new edu.uniroma1.msecs.soapserver.ExamImplService();
                edu.uniroma1.msecs.soapserver.Exam port = service.getExamImplPort();
                // TODO initialize WS operation arguments here
                java.lang.String arg0 = id;
                // TODO process result here
                edu.uniroma1.msecs.soapserver.Professor result = port.getDetails(arg0);
                System.out.println("Ricevuto id: " + id + " con ranking: " + rank + "... bravo " + result.getName() + " " + result.getSurname());
            } catch (Exception ex) {
            // TODO handle custom exceptions here
            }
            
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
    
    private void getDetails(String id){
        try { // Call Web Service Operation
            edu.uniroma1.msecs.soapserver.ExamImplService service = new edu.uniroma1.msecs.soapserver.ExamImplService();
            edu.uniroma1.msecs.soapserver.Exam port = service.getExamImplPort();
            // TODO initialize WS operation arguments here
            java.lang.String arg0 = id;
            // TODO process result here
            edu.uniroma1.msecs.soapserver.Professor result = port.getDetails(arg0);
            System.out.println("Result = "+result);
        } catch (Exception ex) {
        // TODO handle custom exceptions here
        }
    }
    
}


