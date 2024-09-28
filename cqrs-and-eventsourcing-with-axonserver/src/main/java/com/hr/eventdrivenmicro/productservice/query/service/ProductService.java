package com.hr.eventdrivenmicro.productservice.query.service;

import com.hr.eventdrivenmicro.productservice.command.aggregate.ProductAggregate;
import com.hr.eventdrivenmicro.productservice.command.dto.ProductDTO;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.axonframework.modelling.command.Aggregate;
import org.axonframework.modelling.command.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private Repository<ProductAggregate> aggregateRepository;
    public ProductDTO loadProduct(final String productId)
    {
        /*
         NOTE: Operation on Aggregates should either happen in Command handling lifecycle scope or in a UnitOfWork scope.
               here, we are using UnitOfWork
         */
        final UnitOfWork<?> unitOfWork = DefaultUnitOfWork.startAndGet(null);
        try{
			/*
			  Here, the state of the product is loaded from the Aggregate (Not from Product repository).
			  Internally the events are fetched from the event store (AxonServer - in this case) and then replayed to get the current
			  state of the Aggregate.
			 */
            final Aggregate<ProductAggregate> aggregate = aggregateRepository.load(productId);
            unitOfWork.commit();
            return aggregate.invoke(prod -> ProductDTO.builder().productId(prod.getProductId()).name(prod.getName()).price(prod.getPrice()).quantity(prod.getQuantity()).build());
        }catch(Exception e) {
            unitOfWork.rollback();
        }
        return null;
    }
}
