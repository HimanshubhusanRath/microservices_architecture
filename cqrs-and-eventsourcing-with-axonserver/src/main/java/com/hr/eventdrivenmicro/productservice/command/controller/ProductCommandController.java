package com.hr.eventdrivenmicro.productservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.eventdrivenmicro.productservice.command.commands.CreateProductCommand;
import com.hr.eventdrivenmicro.productservice.command.commands.UpdateProductCommand;
import com.hr.eventdrivenmicro.productservice.command.dto.ProductDTO;

@RestController
@RequestMapping("/products")
public class ProductCommandController {

	@Autowired
	private CommandGateway commandGateway;
	
	@PostMapping("/add")
	public String addProduct(@RequestBody final ProductDTO productDTO)
	{
		final CreateProductCommand command = CreateProductCommand.builder()
												.name(productDTO.getName())
												.price(100.00)
												.quantity(3)
												.productId(UUID.randomUUID().toString()).build();
		// Waits till it gets the response
		return commandGateway.sendAndWait(command);
	}
	
	@PostMapping("/update")
	public String updateProduct(@RequestBody final ProductDTO productDTO)
	{
		final UpdateProductCommand command = UpdateProductCommand.builder()
												.productId(productDTO.getProductId())
												.name(productDTO.getName())
												.quantity(productDTO.getQuantity())
												.price(productDTO.getPrice())
												.build();
		return commandGateway.sendAndWait(command);
	}
}
