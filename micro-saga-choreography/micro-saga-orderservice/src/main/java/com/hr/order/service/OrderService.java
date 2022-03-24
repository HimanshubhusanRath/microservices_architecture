package com.hr.order.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hr.common.dto.OrderRequestDTO;
import com.hr.common.event.OrderStatus;
import com.hr.order.entity.PurchaseOrder;
import com.hr.order.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderStatusPublisher orderStatusPublisher;
	
	@Transactional
	public PurchaseOrder createOrder(final OrderRequestDTO orderRequestDTO)
	{
		final PurchaseOrder order = orderRepository.save(getPurchaseOrder(orderRequestDTO));
		// Set the order ID
		orderRequestDTO.setOrderID(order.getId());
		/*
		 * Produce kafka event with status ORDER_CREATED
		 */
		orderStatusPublisher.publishOrderEvent(orderRequestDTO, OrderStatus.ORDER_CREATED);
		return order;
	}

	public List<PurchaseOrder> getAllOrders()
	{
		return orderRepository.findAll();
	}
	
	
	private PurchaseOrder getPurchaseOrder(OrderRequestDTO orderRequestDTO) {
		final PurchaseOrder order = new PurchaseOrder();
		order.setUserId(orderRequestDTO.getUserID());
		order.setAmount(orderRequestDTO.getAmount());
		order.setOrderStatus(OrderStatus.ORDER_CREATED);
		order.setProductId(orderRequestDTO.getProductID());
		return order;
	}
}
