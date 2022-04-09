package com.hr.common.event;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.hr.common.dto.OrderRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderEvent implements Event{

	private UUID eventId = UUID.randomUUID();
	private Date eventDate = Calendar.getInstance().getTime();
	private OrderRequestDTO orderRequestDTO;
	private OrderStatus orderStatus;
	
	public OrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus) {
		super();
		this.orderRequestDTO = orderRequestDTO;
		this.orderStatus = orderStatus;
	}
	
	@Override
	public UUID getEventID() {
		return this.eventId;
	}
	@Override
	public Date getEventDate() {
		return this.eventDate;
	}
	
}
