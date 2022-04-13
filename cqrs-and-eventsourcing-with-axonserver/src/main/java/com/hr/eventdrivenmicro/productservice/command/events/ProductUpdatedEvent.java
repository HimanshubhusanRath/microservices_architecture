package com.hr.eventdrivenmicro.productservice.command.events;

import com.hr.eventdrivenmicro.productservice.command.model.ProductModel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProductUpdatedEvent extends ProductModel{
	private String productId;
	
	
}
