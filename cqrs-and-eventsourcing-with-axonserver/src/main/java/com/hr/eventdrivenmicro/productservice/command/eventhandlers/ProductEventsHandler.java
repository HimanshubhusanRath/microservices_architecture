package com.hr.eventdrivenmicro.productservice.command.eventhandlers;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hr.eventdrivenmicro.productservice.command.entity.Product;
import com.hr.eventdrivenmicro.productservice.command.events.ProductCreatedEvent;
import com.hr.eventdrivenmicro.productservice.command.events.ProductUpdatedEvent;
import com.hr.eventdrivenmicro.productservice.command.repository.ProductRepository;

@Component
public class ProductEventsHandler {

	@Autowired
	private ProductRepository repository;
	
	@EventHandler
	public void handleProductCreatedEvent(final ProductCreatedEvent event)
	{
		final Product product = new Product();
		BeanUtils.copyProperties(event, product);
		repository.save(product);
	}
	
	@EventHandler
	public void handleProductUpdatedEvent(final ProductUpdatedEvent event)
	{
		final Product product = new Product();
		BeanUtils.copyProperties(event, product);
		repository.save(product);
	}
}
