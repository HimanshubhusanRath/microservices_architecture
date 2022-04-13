package com.hr.eventdrivenmicro.productservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.hr.eventdrivenmicro.productservice.command.commands.CreateProductCommand;
import com.hr.eventdrivenmicro.productservice.command.commands.UpdateProductCommand;
import com.hr.eventdrivenmicro.productservice.command.events.ProductCreatedEvent;
import com.hr.eventdrivenmicro.productservice.command.events.ProductUpdatedEvent;
import com.hr.eventdrivenmicro.productservice.command.model.ProductModel;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Aggregate
@SuperBuilder
@NoArgsConstructor
public class ProductAggregate extends ProductModel{

	@AggregateIdentifier
	private String productId;
	
	
	/**
	 * Create an aggregate using the command and start the life cycle of the aggregate
	 * 
	 * @param command
	 */
	@CommandHandler
	public ProductAggregate(final CreateProductCommand command)
	{
		final ProductCreatedEvent event = new ProductCreatedEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}
	
	/**
	 *  Handle the update product command
	 */
	@CommandHandler
	public void handle(final UpdateProductCommand command)
	{
		final ProductUpdatedEvent event = new ProductUpdatedEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
		
	}
	
	@EventSourcingHandler
	public void on(final ProductCreatedEvent event)
	{
		this.name = event.getName();
		this.productId = event.getProductId();
		this.price = event.getPrice();
		this.quantity = event.getQuantity();
	}
	
	@EventSourcingHandler
	public void on(final ProductUpdatedEvent event)
	{
		this.name = event.getName();
		this.productId = event.getProductId();
		this.price = event.getPrice();
		this.quantity = event.getQuantity();
	}
	
}
