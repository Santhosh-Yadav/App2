package in.santhosh.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.santhosh.config.SendAndRecieveJMSMessage;

@RestController
public class IRCTRailwayController {

	@Autowired
	private SendAndRecieveJMSMessage jmsMessage;

	@GetMapping("/message")
	public String consumeMessage() {
		
		System.out.println("Inside consumeMessage");
	
		try {
			Object receiveMessage = jmsMessage.receiveMessage();
			System.out.println("Rec Msg :: " + receiveMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return "Message Consumed";

	}
}
