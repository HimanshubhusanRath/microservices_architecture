package com.hr.microservice.logging.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.microservice.logging.dto.User;

@RestController
@RequestMapping("/user")
public class UserController {

	private final Logger LOG = LoggerFactory.getLogger(UserController.class); 
	
	private Map<Integer,User> users;
	
	@PostConstruct
	public void initialize()
	{
		users = new HashMap<Integer, User>();
		users.put(1, new User(1,"himanshu"));
		users.put(2, new User(2,"Rajesh"));
		users.put(3, new User(3,"Amar"));
	}
	
	@GetMapping("/{id}")
	public User getUser(@PathVariable final Integer id)
	{
		final User result = users.get(id);
		if(null!=result)
		{
			LOG.info("User found for the given ID {}", result);
			return result;
		}
		else
		{
			LOG.error("No user found :( ");
			return null;
		}
	}
	
	
}
