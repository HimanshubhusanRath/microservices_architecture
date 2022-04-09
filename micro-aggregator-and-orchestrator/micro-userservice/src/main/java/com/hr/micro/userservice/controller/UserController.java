package com.hr.micro.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.micro.userservice.dto.UserDTO;
import com.hr.micro.userservice.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}")
	public UserDTO getUserById(@PathVariable final Integer userId)
	{
		return userService.getUser(userId);
	}
	
	@PostMapping("/add")
	public void addUser(@RequestBody final UserDTO userDTO)
	{
		userService.addUser(userDTO);
	}
	
}
