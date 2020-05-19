package com.flightcheckin.jms.hm.checkin;

import com.flightcheckin.jms.hm.model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProducerAck {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");
		Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");

		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
			JMSContext jmsContext = cf.createContext()) {

			JMSProducer producer = jmsContext.createProducer();
			ObjectMessage objectMessage = jmsContext.createObjectMessage();

			Passenger passenger = new Passenger();
			passenger.setId(123);
			passenger.setFirstName("Bob");
			passenger.setLastName("Fisher");
			passenger.setPhone("666666666");
			passenger.setEmail("bobbigfisher@gmail.com");

			objectMessage.setObject(passenger);

			producer.send(requestQueue, passenger);
		}
	}
}
