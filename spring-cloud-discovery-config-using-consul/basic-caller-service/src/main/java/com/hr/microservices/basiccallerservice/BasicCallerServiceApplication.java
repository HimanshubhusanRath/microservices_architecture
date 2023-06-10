package com.hr.microservices.basiccallerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class BasicCallerServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BasicCallerServiceApplication.class, args);
	}
	@LoadBalanced
	@Bean
	RestTemplate template()
	{
		return new RestTemplate();
	}
}
