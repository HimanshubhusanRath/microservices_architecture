package com.hr.common.dto;

import com.hr.common.event.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
	
	private Integer userID;
	private Integer orderID;
	private Integer amount;
	private Integer productID;
	private OrderStatus orderStatus;

}
