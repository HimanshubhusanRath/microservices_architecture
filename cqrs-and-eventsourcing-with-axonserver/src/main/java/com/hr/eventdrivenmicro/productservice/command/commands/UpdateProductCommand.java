package com.hr.eventdrivenmicro.productservice.command.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import com.hr.eventdrivenmicro.productservice.command.model.ProductModel;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
public class UpdateProductCommand extends ProductModel{
	
	@TargetAggregateIdentifier
	private String productId;
	
}
