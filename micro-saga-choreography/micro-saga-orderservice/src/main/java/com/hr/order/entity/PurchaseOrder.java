package com.hr.order.entity;

import javax.persistence.*;

import com.hr.common.event.OrderStatus;
import com.hr.common.event.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PURCHASE_ORDER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrder {

	@Id
	@GeneratedValue
	private Integer id;
	
	private Integer userId;
	private Integer productId;
	private Integer amount;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	
	
}
