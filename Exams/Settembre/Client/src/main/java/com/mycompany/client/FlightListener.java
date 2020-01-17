/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.client;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
public class FlightListener implements MessageListener{
    
    private Connection db_connection;
    private Properties props;
    private InitialContext intCont;
    private TopicConnectionFactory cf;
    private TopicConnection connection;
    private TopicSession session;
    private Topic destination, destination1;
    private TopicPublisher producer;
    private TopicSubscriber o_sub, q_sub;       //order and quotation subscribers

    public FlightListener() {
        props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL, "tcp://localhost:61616");
        try {
            intCont = new InitialContext(props);
            cf = (TopicConnectionFactory) intCont.lookup("ConnectionFactory");
            destination = (Topic) intCont.lookup("dynamicTopics/Flights");
            connection = cf.createTopicConnection();
            session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
            q_sub = session.createSubscriber(destination);
            q_sub.setMessageListener(this);
            connection.start();
            Class.forName("org.sqlite.JDBC");
        } catch (Exception ex) {
            Logger.getLogger(FlightListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onMessage(Message msg) {
        TextMessage mess = (TextMessage) msg;
        Random randomeGen = new Random();
        try {
            String code = mess.getStringProperty("flight");
            String status = mess.getText().split(":")[1].replace(" ", "");
            
            PreparedStatement statement = db_connection.prepareStatement("INSERT INTO flights VALUES(?, ?);");
            statement.setQueryTimeout(30);
            statement.setString(1, code);
            statement.setString(2, status);
            statement.executeUpdate();
            
            System.out.println(String.format("%s -> %s", code, status));
            
        } catch (JMSException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(FlightListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void start() {
        try {
            db_connection = DriverManager.getConnection("jdbc:sqlite:/home/biar/se-2019_09.db");
        } catch ( SQLException err) {
            err.printStackTrace();
        }
    }


    public void stop() {
        try {
            connection.stop();
            if (db_connection != null) {
                db_connection.close();
            }
        } catch (JMSException | SQLException err) {
            err.printStackTrace();
        }
    }
    
}
