/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fortunato.jmsclientfortunato;

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
public class OrderManager implements MessageListener {
    private Properties props;
    private InitialContext intCont;
    private TopicConnectionFactory cf;
    private TopicConnection connection;
    private TopicSession session;
    private Topic destination, destination1;
    private TopicPublisher producer;
    private TopicSubscriber o_sub, q_sub;
    private String user;

    public OrderManager(String user) {
        this.user = user;
        props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        try {
            intCont = new InitialContext(props);
            cf = (TopicConnectionFactory) intCont.lookup("ConnectionFactory");
            destination = (Topic) intCont.lookup("dynamicTopics/Quotations");
            destination1 = (Topic) intCont.lookup("dynamicTopics/Orders");
            connection = cf.createTopicConnection();
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            producer = session.createPublisher(destination1);
            q_sub= session.createSubscriber(destination);
            o_sub=session.createSubscriber(destination1);
            q_sub.setMessageListener(this);
            o_sub.setMessageListener(this);
            connection.start();
        } catch (Exception ex) {
            Logger.getLogger(OrderManager.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }

    @Override
    public void onMessage(Message msg) {
        TextMessage mess=(TextMessage) msg;
        Random randomGen=new Random();
        try {
            Enumeration props=mess.getPropertyNames();
            while(props.hasMoreElements()){
             System.out.print(mess.getStringProperty(props.nextElement().toString())+ " - ");   
            }
            System.out.println();
            if(mess.propertyExists("Quotation") && mess.getStringProperty("Quotation").equals("Barilla")){
            TextMessage reply=session.createTextMessage();
            reply.setStringProperty("User", user);
            reply.setStringProperty("Quotation", mess.getStringProperty("Quotation"));
            reply.setIntProperty("Amount", randomGen.nextInt());
            reply.setDoubleProperty("Price", mess.getDoubleProperty("Price"));
            producer.send(reply);
            System.out.println("sent order");
            }
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }
}
