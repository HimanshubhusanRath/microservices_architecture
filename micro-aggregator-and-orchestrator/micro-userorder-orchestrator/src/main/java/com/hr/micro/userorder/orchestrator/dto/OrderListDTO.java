package com.hr.micro.userorder.orchestrator.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDTO {

	private List<OrderDTO> orderDtos;
}
