package in.santhosh.config;

import java.sql.Timestamp;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import in.santhosh.exception.PosidexException;

@Component
public class SendAndRecieveJMSMessage {
	@Autowired
	private Environment environment;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${PRIME360_RESPONSE_QUEUE}")
	private String RESPONSE_QUEUE;
	
	@Value("${PRIME360_REQUEST_QUEUE}")
	private String REQUEST_QUEUE;
	
	@Value("${PRIME360_RESPONSE_QUEUE_INTRADAY}")
	private String RESPONSE_QUEUE_INTRADAY;
	
	//Logger logger = Logger.getLogger("SendAndReceiveJMSMessage");
	
	private static final String JMS_VALIDATION_MSG = "error while sending message to queue";
	
	private static final String RESPONSE_VALIDATION = "Active mq is down!";
	
	
	public void sendMessage(String clientMessage, String requestId) throws PosidexException {
		//logger.info("inside sendMeesage method");
		//logger.info("before sending message :: " + new Timestamp(System.currentTimeMillis()));
		try {
			jmsTemplate.send(RESPONSE_QUEUE, new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = (session).createTextMessage(clientMessage);
					message.setStringProperty("PSX_PSX_ID", String.valueOf(requestId));
					return message;
				}
			});
		} catch (Exception e) {
			//logger.error(JMS_VALIDATION_MSG + e.getMessage());
			throw new PosidexException(RESPONSE_VALIDATION);
		}
	}

	public Object receiveMessage() throws PosidexException {
		Object receivedMessage = null;
		System.out.println("inside receiveMessage method IRCTRailway Class");
		try {
			//jmsTemplate.setReceiveTimeout(Integer.parseInt(environment.getProperty("recieveTimeout")));
			receivedMessage = jmsTemplate.receiveSelected(REQUEST_QUEUE);
			System.out.println("inside receiveMessage method try");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("inside receiveMessage method catch");
			//logger.error(JMS_VALIDATION_MSG + e.getMessage());
			//throw new PosidexException(RESPONSE_VALIDATION);
		}
	//	logger.info("after recieve message::" + new Timestamp(System.currentTimeMillis()));
		return receivedMessage;

	}
	
	public Object receiveAddToTargetMessage(String requestId,String queue) throws PosidexException {
		Object receivedMessage = null;
	//	logger.info("inside receiveMessage method");
		try {
			jmsTemplate.setReceiveTimeout(Integer.parseInt(environment.getProperty("recieveTimeout")));
			receivedMessage = jmsTemplate.receiveSelected(queue,
					"PSX_BATCH_ID='" + requestId + "'");
		} catch (Exception e) {
		//	logger.error(JMS_VALIDATION_MSG + e.getMessage());
			throw new PosidexException(RESPONSE_VALIDATION);
		}
	//	logger.info("after recieve message :: " + new Timestamp(System.currentTimeMillis()));
		return receivedMessage;
	}
	
	public void sendAddToTargetMessage(String clientMessage,String psxBatchId) throws PosidexException {

	//	logger.info("inside sendMessage method");

	//	logger.info("before sending message :: " + new Timestamp(System.currentTimeMillis()));
		try {
			jmsTemplate.send(REQUEST_QUEUE, new MessageCreator() {

				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = (session).createTextMessage(clientMessage);
					message.setStringProperty("PSX_BATCH_ID", String.valueOf(psxBatchId));
					return message;
				}
			});
		} catch (Exception e) {
	//		logger.error(JMS_VALIDATION_MSG + e.getMessage());
			throw new PosidexException(RESPONSE_VALIDATION);
		}

	}
}
