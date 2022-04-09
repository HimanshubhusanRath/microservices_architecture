package com.hr.micro.userorder.orchestrator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.micro.userorder.orchestrator.dto.UserOrderDetailsDTO;
import com.hr.micro.userorder.orchestrator.service.UserOrderOrchestratorService;

@RestController
@RequestMapping("/userorder-orc/")
public class UserOrderOrchestratorController {

	@Autowired
	private UserOrderOrchestratorService service;
	
	@GetMapping("/user/{userId}")
	public UserOrderDetailsDTO getUserOrderDetails(@PathVariable final String userId)
	{
		return service.getUserOrderDetails(userId);
	}
}
