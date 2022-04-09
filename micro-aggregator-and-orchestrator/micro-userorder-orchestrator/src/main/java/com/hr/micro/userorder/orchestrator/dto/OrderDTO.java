package com.hr.micro.userorder.orchestrator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	private Integer id;
	private Integer userId;
	private Integer productId;
	private Integer amount;

}
