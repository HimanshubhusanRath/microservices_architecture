package com.hr.springboot.websocket.chatapp.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.hr.springboot.websocket.chatapp.model.ChatMessage;

@Controller
public class ChatController {

	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessage register(@Payload final ChatMessage chatMessage, final SimpMessageHeaderAccessor headerAccessor)
	{
		 // Set the user name in the user's session. Separate sessions are created for separate users
		 headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
		 return chatMessage;
	}
	
	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload final ChatMessage chatMessage, final SimpMessageHeaderAccessor headerAccessor)
	{
		// Set an user specific message formatting
		if(headerAccessor.getSessionAttributes().get("username").equals("A"))
		 {
			 chatMessage.setContent(chatMessage.getContent()+ " -- by A");
		 }
		
		return chatMessage;
	}
}
