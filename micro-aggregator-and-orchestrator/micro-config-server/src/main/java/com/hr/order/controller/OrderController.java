package com.hr.order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hr.common.dto.OrderRequestDTO;
import com.hr.order.entity.PurchaseOrder;
import com.hr.order.service.OrderService;

import java.util.List;

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

	@GetMapping("/all")
	public List<PurchaseOrder> getAllOrders()
	{
		return orderService.getAllOrders();
	}
	
	@GetMapping("/user/{userId}")
	public List<PurchaseOrder> getOrdersForUser(@PathVariable final Integer userId)
	{
		return orderService.getOrdersForUser(userId);
	}
}
