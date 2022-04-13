package com.hr.eventdrivenmicro.productservice.query.projection;

import java.util.List;
import java.util.stream.Collectors;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hr.eventdrivenmicro.productservice.command.model.ProductModel;
import com.hr.eventdrivenmicro.productservice.command.repository.ProductRepository;
import com.hr.eventdrivenmicro.productservice.query.model.ProductQuery;

@Component
public class ProductProjection {

	@Autowired
	private ProductRepository productRepository;
	
	@QueryHandler
	public List<ProductModel> handle(final ProductQuery query)
	{
		return productRepository.findAll().stream().map(prod -> 
			ProductModel.builder()
				.name(prod.getName())
				.quantity(prod.getQuantity())
				.price(prod.getPrice()).build()).collect(Collectors.toList());
	}
}
