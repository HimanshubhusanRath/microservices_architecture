package com.hr.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.common.dto.OrderRequestDTO;
import com.hr.common.event.OrderEvent;
import com.hr.common.event.OrderStatus;

import reactor.core.publisher.Sinks;

@Service
public class OrderStatusPublisher {

	@Autowired
	private Sinks.Many<OrderEvent> orderSinks;
	
	public void publishOrderEvent(final OrderRequestDTO orderRequestDTO, final OrderStatus orderStatus)
	{
		final OrderEvent event = new OrderEvent(orderRequestDTO, orderStatus);
		orderSinks.tryEmitNext(event);
	}
}
