package com.hr.eventdrivenmicro.productservice.query.controller;

import com.hr.eventdrivenmicro.productservice.command.dto.ProductDTO;
import com.hr.eventdrivenmicro.productservice.command.model.ProductModel;
import com.hr.eventdrivenmicro.productservice.query.model.ProductQuery;
import com.hr.eventdrivenmicro.productservice.query.service.ProductService;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductQueryController {
    @Autowired
    private QueryGateway queryGateway;
    @Autowired
    private EventStore eventStore;
	@Autowired
	private ProductService productService;

    @GetMapping("/get-all")
    public List<ProductModel> getAllProducts() {
        final ProductQuery query = new ProductQuery();
        return queryGateway.query(query, ResponseTypes.multipleInstancesOf(ProductModel.class)).join();
    }

    @GetMapping("/{product-id}/get-events")
    public List<EventMessage<?>> getAllEvents(@PathVariable("product-id") final String productId) {
        return eventStore.readEvents(productId).asStream().collect(Collectors.toList());
    }

    @GetMapping("/{product-id}/load")
    public ProductDTO loadProduct(@PathVariable("product-id") final String productId) {
        return productService.loadProduct(productId);
    }


}
