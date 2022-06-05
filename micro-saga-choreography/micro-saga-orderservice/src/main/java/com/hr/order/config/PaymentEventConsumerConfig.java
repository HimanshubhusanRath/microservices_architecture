package com.hr.order.config;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hr.common.event.PaymentEvent;
import com.hr.order.service.PaymentEventHandler;

@Configuration
public class PaymentEventConsumerConfig {

	@Autowired
	private PaymentEventHandler paymentEventHandler;
	
	@Bean
	public Consumer<PaymentEvent> paymentEventConsumer()
	{
		return (event) -> paymentEventHandler.handlePaymentEvent(event);
	}
}
