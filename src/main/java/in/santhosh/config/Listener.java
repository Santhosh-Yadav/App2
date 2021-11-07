package in.santhosh.config;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import in.santhosh.exception.PosidexException;
import in.santhosh.response.ResponseMessageBuilder;
import in.santhosh.service.IRCTRailwayService;

@Component
public class Listener {
	
	Logger logger = Logger.getLogger(Listener.class);
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private IRCTRailwayService service;
	
	
	@JmsListener(destination = "PRIME360_REQUEST_QUEUE")
	public void consumeMsg(String msg) throws PosidexException {
		
		logger.info("In Listener class consumeMsg method");
		JSONObject jsonObject = null;
		String from = null;
		String to = null;
		String requestId = null;
		String trainNumber = null;
		String trainName = null;
		logger.info("Dequeued Request Message :: "+msg);
		
		try {
			 jsonObject = new JSONObject(msg);
			 from = jsonObject.getString("from");
			 to = jsonObject.getString("to");
			 requestId = jsonObject.getString("requestId");
			 trainNumber = jsonObject.getString("trainNo");
			 trainName = jsonObject.getString("name");
			 
			 
		} catch (JSONException e1) {
			
			e1.printStackTrace();
		}
		
		int count  = service.getResponse(requestId, from, to,trainNumber,trainName);
		
		ResponseMessageBuilder builder = new ResponseMessageBuilder();
		
		if ( count > 0) {
			
			builder.setRequestStatus("C");
			builder.setStatusMessage("Request Processed Successfully");
			
		}
		
		else {
			builder.setRequestStatus("E");
			builder.setStatusMessage("Request Process Failed");
		}
		
		Gson gson = new Gson();
		String responseMessage = gson.toJson(builder);
		logger.info("Enqueud Response Message :: "+responseMessage);
		
		
		try {
			jmsTemplate.send("PRIME360_RESPONSE_QUEUE", new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					Message message = (session).createTextMessage(responseMessage);
					return message;
				}
			});
		} catch (Exception e) {
			logger.error("Error while sending message to Queue");
			throw new PosidexException("Error while sending resposne message to Queue");
		}
		
	}

}
