package com.hr.eventdrivenmicro.productservice.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.hr.eventdrivenmicro.productservice.command.model.ProductModel;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class CreateProductCommand extends ProductModel{

	
	/**
	 * This {@code TargetAggregateIdentifier} annotation is used to match the command and aggregate object
	 */
	@TargetAggregateIdentifier
	private String productId;
	
	
}
