package com.flightcheckin.jms.hm.checkin;

import com.flightcheckin.jms.hm.model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ProducerTransactions {

	public static void main(String[] args) throws NamingException, JMSException {

		InitialContext initialContext = new InitialContext();
		Queue requestQueue = (Queue) initialContext.lookup("queue/requestQueue");

		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
			JMSContext jmsContext = cf.createContext(JMSContext.SESSION_TRANSACTED)) {

			JMSProducer producer = jmsContext.createProducer();

			ObjectMessage objectMessage1 = jmsContext.createObjectMessage();
			ObjectMessage objectMessage2 = jmsContext.createObjectMessage();

			Passenger passenger1 = new Passenger();
			passenger1.setId(123);
			passenger1.setFirstName("Abe");
			passenger1.setLastName("Smith");
			passenger1.setEmail("abesmith@gmail.com");
			passenger1.setPhone("666666664");

			Passenger passenger2 = new Passenger();
			passenger2.setId(456);
			passenger2.setFirstName("Grant");
			passenger2.setLastName("Doe");
			passenger2.setEmail("grantdoe@gmail.com");
			passenger2.setPhone("666666665");

			objectMessage1.setObject(passenger1);
			producer.send(requestQueue, passenger1);

			objectMessage2.setObject(passenger2);
			producer.send(requestQueue, passenger2);

			jmsContext.commit();

			ObjectMessage objectMessage3 = jmsContext.createObjectMessage();
			Passenger passenger3 = new Passenger();
			passenger3.setId(123);
			passenger3.setFirstName("Bob");
			passenger3.setLastName("Fisher");
			passenger3.setPhone("666666666");
			passenger3.setEmail("bobbigfisher@gmail.com");

			objectMessage3.setObject(passenger3);
			producer.send(requestQueue, passenger3);
		}
	}
}
