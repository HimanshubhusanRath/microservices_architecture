package com.hr.common.dto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HrSpringdemoApplication {

	@GetMapping("/message")
	public String getMessage()
	{
		return "Your Project is now successfully deployed to Azure ! ";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(HrSpringdemoApplication.class, args);
	}

}
