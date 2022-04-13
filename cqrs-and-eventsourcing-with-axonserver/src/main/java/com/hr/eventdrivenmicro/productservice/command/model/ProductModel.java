package com.hr.eventdrivenmicro.productservice.command.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
public class ProductModel {

	protected String name;
	protected double price;
	protected Integer quantity;
	
}
