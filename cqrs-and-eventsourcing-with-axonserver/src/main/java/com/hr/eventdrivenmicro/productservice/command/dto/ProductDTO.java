package com.hr.eventdrivenmicro.productservice.command.dto;

import com.hr.eventdrivenmicro.productservice.command.model.ProductModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO extends ProductModel{
	
	private String productId;
}
