package com.hr.oauth.resource.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hr.oauth.resource.server.config.ServicesConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ServicesConfiguration.class)
public class ServerBMainApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerBMainApplication.class, args);
	}

}
