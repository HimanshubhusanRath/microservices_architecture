package com.hr.common.event;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import com.hr.common.dto.PaymentRequestDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentEvent implements Event{

	private UUID eventId = UUID.randomUUID();
	private Date eventDate = Calendar.getInstance().getTime();
	private PaymentRequestDTO paymentRequestDTO;
	private PaymentStatus paymentStatus;
	
	public PaymentEvent(PaymentRequestDTO paymentRequestDTO, PaymentStatus paymentStatus) {
		super();
		this.paymentRequestDTO = paymentRequestDTO;
		this.paymentStatus = paymentStatus;
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
