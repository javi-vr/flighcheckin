package com.flightcheckin.jms.hm.reservation;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConsumerAck {

    public static void main(String[] args) throws NamingException, InterruptedException, JMSException {

        InitialContext initialContext = new InitialContext();
        // lookup for the RequestQueue
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            JMSContext jmsContext = cf.createContext(JMSContext.CLIENT_ACKNOWLEDGE)) {

            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            ObjectMessage message;

            message = (ObjectMessage) consumer.receive();
            System.out.println("Consumer1: " + message);
            message.acknowledge();

        }
    }
}

