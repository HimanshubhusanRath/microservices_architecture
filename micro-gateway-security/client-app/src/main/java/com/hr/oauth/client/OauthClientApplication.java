package com.hr.oauth.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.hr.oauth.client.config.ServicesConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ServicesConfiguration.class)
public class OauthClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(OauthClientApplication.class, args);
	}

}
