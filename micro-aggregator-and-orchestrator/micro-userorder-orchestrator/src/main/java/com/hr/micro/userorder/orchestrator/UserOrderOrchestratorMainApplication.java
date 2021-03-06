package com.hr.micro.userorder.orchestrator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class UserOrderOrchestratorMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserOrderOrchestratorMainApplication.class, args);
	}
	
	@LoadBalanced
	@Bean
	RestTemplate template()
	{
		return new RestTemplate();
	}
}
