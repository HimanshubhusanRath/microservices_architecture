package com.hr.micro.userorder.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrderDTO {

	private Integer userID;
	private int orderCount;
	
}