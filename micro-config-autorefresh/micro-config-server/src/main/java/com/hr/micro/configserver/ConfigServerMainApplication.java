package com.hr.micro.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerMainApplication.class, args);
	}
	
	@LoadBalanced
	@Bean
	RestTemplate template()
	{
		return new RestTemplate();
	}
}
