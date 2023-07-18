package com.hr.payment.config;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hr.common.event.OrderEvent;
import com.hr.common.event.OrderStatus;
import com.hr.common.event.PaymentEvent;
import com.hr.payment.service.PaymentService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class PaymentProcessorConfig {

	@Autowired
	private PaymentService paymentService;
	
	/*
	 * Listens to OrderEvent and generates PaymentEvent
	 */
	@Bean
	public Function<Flux<OrderEvent>, Flux<PaymentEvent>> paymentProcessor()
	{
		return orderEventFlux -> orderEventFlux.flatMap(this::processPayment);
	}
	
	private Mono<PaymentEvent> processPayment(final OrderEvent orderEvent)
	{
		if(OrderStatus.ORDER_CREATED.equals(orderEvent.getOrderStatus()))
		{
			return Mono.fromSupplier(() -> this.paymentService.newOrderEvent(orderEvent));
		}
		else
		{
			return Mono.fromRunnable(() -> this.paymentService.cancelOrderEvent(orderEvent));
		}
	}
}
