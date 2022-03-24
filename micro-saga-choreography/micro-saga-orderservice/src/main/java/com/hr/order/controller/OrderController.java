package com.hr.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.common.dto.OrderRequestDTO;
import com.hr.order.entity.PurchaseOrder;
import com.hr.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderService orderService;
	
	@PostMapping("/create")
	public PurchaseOrder createOrder(@RequestBody final OrderRequestDTO orderRequestDTO)
	{
		return orderService.createOrder(orderRequestDTO);
	}
}
