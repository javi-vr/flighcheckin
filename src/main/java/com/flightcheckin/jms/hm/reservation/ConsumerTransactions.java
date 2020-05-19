package com.flightcheckin.jms.hm.reservation;

import com.flightcheckin.jms.hm.model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConsumerTransactions {

    public static void main(String[] args) throws NamingException, InterruptedException, JMSException {

        InitialContext initialContext = new InitialContext();
        Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

        try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
            JMSContext jmsContext = cf.createContext()) {

            JMSConsumer consumer = jmsContext.createConsumer(requestQueue);
            Passenger passenger = consumer.receiveBody(Passenger.class);
            System.out.println("Passenger Name: " + passenger.getFirstName() + " " + passenger.getLastName());
        }
    }
}
