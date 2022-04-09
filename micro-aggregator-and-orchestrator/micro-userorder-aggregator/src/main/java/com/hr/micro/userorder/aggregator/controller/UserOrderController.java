package com.hr.micro.userorder.aggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.micro.userorder.aggregator.dto.UserOrderDTO;
import com.hr.micro.userorder.aggregator.service.UserOrderService;

@RestController
@RequestMapping("/userorder-agg")
public class UserOrderController {

	@Autowired
	private UserOrderService service;
	
	@GetMapping("/user/{userId}")
	public UserOrderDTO getUserOrderDetails(@PathVariable final Integer userId)
	{
		return service.getUserOrderDetails(userId);
	}
}
