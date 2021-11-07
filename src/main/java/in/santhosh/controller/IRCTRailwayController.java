package in.santhosh.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.santhosh.config.Listener;
import in.santhosh.config.SendAndRecieveJMSMessage;

@RestController
public class IRCTRailwayController {
	
	Logger logger = Logger.getLogger(IRCTRailwayController.class);

	@Autowired
	private SendAndRecieveJMSMessage jmsMessage;
	
	@Autowired
	private Listener listen;

	@GetMapping("/message")
	public String consumeMessage() {
		
		logger.info("In consumeMessage method");
		
	
		/*
		 * try { Object receiveMessage = jmsMessage.receiveMessage();
		 * System.out.println("Rec Msg :: " + receiveMessage); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		 
		return "Message Consumed";

	}
}
