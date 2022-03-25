package com.hr.order.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.common.dto.PaymentRequestDTO;
import com.hr.common.event.OrderStatus;
import com.hr.common.event.PaymentEvent;
import com.hr.common.event.PaymentStatus;
import com.hr.order.repository.OrderRepository;

@Service
public class PaymentEventHandler {

	@Autowired
	private OrderRepository orderRepository;
	
	/**
	 * 1. Find the order by ID
	 * 2. If the payment status is successful, update the order status as completed
	 * 3. Else update the order status as failed 
	 * 
	 * @param event
	 */
	@Transactional
	public void handlePaymentEvent(PaymentEvent event) {
		final PaymentRequestDTO paymentReqDTO = event.getPaymentRequestDTO();
		final int orderID = paymentReqDTO.getOrderID();
		
		orderRepository.findById(orderID).ifPresent(order -> {
			final OrderStatus orderStatus = PaymentStatus.PAYMENT_SUCCESS.equals(event.getPaymentStatus()) ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
			order.setOrderStatus(orderStatus);
			order.setPaymentStatus(event.getPaymentStatus());
			orderRepository.save(order);
		});
		
	}

	
}
