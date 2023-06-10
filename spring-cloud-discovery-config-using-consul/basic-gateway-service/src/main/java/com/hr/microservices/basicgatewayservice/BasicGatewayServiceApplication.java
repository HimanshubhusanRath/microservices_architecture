package com.hr.microservices.basicgatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BasicGatewayServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(BasicGatewayServiceApplication.class, args);
	}

}
