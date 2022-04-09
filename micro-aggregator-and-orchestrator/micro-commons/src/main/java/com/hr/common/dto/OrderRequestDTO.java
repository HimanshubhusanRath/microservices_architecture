package com.hr.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {
	private Integer userID;
	private Integer orderID;
	private Integer amount;
	private Integer productID;

}
