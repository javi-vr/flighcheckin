package com.flightcheckin.jms.hm.reservation.listeners;

import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.flightcheckin.jms.hm.model.Passenger;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

public class EligibilityCheckListener implements MessageListener {

	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;

		try(ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
			JMSContext jmsContext = cf.createContext()) {

			InitialContext initialContext = new InitialContext();
			Queue replyQueue = (Queue) initialContext.lookup("queue/replyQueue");
			MapMessage replyMessage = jmsContext.createMapMessage();

			Passenger passenger = (Passenger) objectMessage.getObject();
			System.out.println("Passenger id: " + passenger.getId());
			System.out.println("Passenger name: " + passenger.getFirstName() + " " + passenger.getLastName());
			System.out.println("Passenger phone number: " + passenger.getPhone());
			System.out.println("Passenger email: " + passenger.getEmail());


			replyMessage.setBoolean("checkedin", true);

			JMSProducer producer = jmsContext.createProducer();
			producer.send(replyQueue, replyMessage);

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();

		}
	}
}