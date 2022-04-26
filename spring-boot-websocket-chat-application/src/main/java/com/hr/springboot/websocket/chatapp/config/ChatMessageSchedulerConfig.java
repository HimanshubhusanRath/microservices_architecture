package com.hr.springboot.websocket.chatapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.hr.springboot.websocket.chatapp.model.ChatMessage;

@Configuration
@EnableScheduling
public class ChatMessageSchedulerConfig {

	@Autowired
	private SimpMessagingTemplate template;
	
	@Scheduled(fixedDelay = 5000)
	public void sendMessage()
	{
		template.convertAndSend("/topic/public", new ChatMessage("HELLO EVERYONE :)", "SYSTEM", ChatMessage.MessageType.CHAT));
	}
	
}
