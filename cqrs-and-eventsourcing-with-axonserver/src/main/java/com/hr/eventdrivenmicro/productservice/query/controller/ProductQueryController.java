package com.hr.eventdrivenmicro.productservice.query.controller;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hr.eventdrivenmicro.productservice.command.model.ProductModel;
import com.hr.eventdrivenmicro.productservice.query.model.ProductQuery;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

	@Autowired
	private QueryGateway queryGateway;
	
	@GetMapping("/get-all")
	public List<ProductModel> getAllProducts()
	{
		final ProductQuery query = new ProductQuery();
		return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ProductModel.class)).join();
	}
	
}
