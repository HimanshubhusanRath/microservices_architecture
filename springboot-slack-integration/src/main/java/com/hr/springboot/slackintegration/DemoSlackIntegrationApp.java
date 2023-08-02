package com.hr.springboot.slackintegration;

import com.hr.springboot.slackintegration.services.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@RequestMapping("/user")

public class DemoSlackIntegrationApp {

	@Autowired
	private SlackService slackService;

	@GetMapping(path = "/greet")
	public void greet(@RequestParam("message")final String message)
	{
		System.out.println("HERE");
		slackService.notifySlack(message);
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoSlackIntegrationApp.class, args);
	}

}
