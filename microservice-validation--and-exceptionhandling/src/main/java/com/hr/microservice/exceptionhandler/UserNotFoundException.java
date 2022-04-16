package com.hr.microservice.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserNotFoundException extends Exception {

	private String message;
	private String code; 
	
}
