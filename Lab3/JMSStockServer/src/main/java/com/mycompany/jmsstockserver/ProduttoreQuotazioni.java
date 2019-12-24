/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jmsstockserver;

import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author biar
 */
public class ProduttoreQuotazioni {
    
        final String titoli[] = { "Telecom", "Finmeccanica", "Banca_Intesa", "Oracle", "Parmalat", "Mondadori", "Vodafone", "Barilla" };

	private String scegliTitolo() {
		int whichMsg;
		Random randomGen = new Random();

		whichMsg = randomGen.nextInt(this.titoli.length);
		return this.titoli[whichMsg];
	}

	private float valore() {
		Random randomGen = new Random();
		float val = randomGen.nextFloat() * this.titoli.length * 10;
		return val;
	}

        private static final  org.slf4j.Logger LOG = LoggerFactory.getLogger(ProduttoreQuotazioni.class);
        
        public void start(){
            Context jndiContext = null;
            ConnectionFactory connectionFactory = null;
            Connection connection = null;
            Session session = null;
            Destination destination = null;
            MessageProducer producer = null;
            String destinationName = "dynamicTopics/Quotazioni";
            
            Properties props = new Properties();
            props.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.activemq.jndi.ActiveMQInitialContextFactory");
            props.setProperty(Context.PROVIDER_URL,"tcp://localhost:61616");
            try {
                jndiContext = new InitialContext(props);
            } catch (NamingException ex) {
                Logger.getLogger(ProduttoreQuotazioni.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
            }
            
            try {
                connectionFactory = (ConnectionFactory) jndiContext.lookup("ConnectionFactory");
                destination = (Destination) jndiContext.lookup(destinationName);
            } catch (NamingException ex) {
                Logger.getLogger(ProduttoreQuotazioni.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            /*
            * Create connection. Create session from connection; false means
            * session is not transacted. Create sender and text message. Send
            * messages, varying text slightly. Send end-of-messages message.
            * Finally, close connection.
            */
            
            
            try {
                connection = connectionFactory.createConnection();
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                producer = session.createProducer(destination);
                
                TextMessage message = null;
                String messageType = null;
                
                message = session.createTextMessage();
                float quotazione;
                int i = 0;
                while (true){
                    i++;
                    messageType = scegliTitolo();
                    quotazione = valore();
                    message.setStringProperty("Nome", messageType);
                    message.setFloatProperty("Valore", quotazione);
                    message.setText("Item " + i + ": " + messageType + ", Valore: " + quotazione);
                    LOG.info(this.getClass().getName() + "Invio quotazione: " + message.getText());
                    producer.send(message);
                    try {
                        Thread.sleep(5000);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                
                
            } catch (JMSException ex) {
                Logger.getLogger(ProduttoreQuotazioni.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (connection != null){
                    try {
                        connection.close();
                    } catch (JMSException ex) {
                        Logger.getLogger(ProduttoreQuotazioni.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            
            
        }
        
}
