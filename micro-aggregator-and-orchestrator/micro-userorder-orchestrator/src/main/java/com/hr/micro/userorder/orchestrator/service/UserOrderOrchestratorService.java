package com.hr.micro.userorder.orchestrator.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hr.micro.userorder.orchestrator.dto.OrderDTO;
import com.hr.micro.userorder.orchestrator.dto.UserDTO;
import com.hr.micro.userorder.orchestrator.dto.UserOrderDetailsDTO;

@Service
public class UserOrderOrchestratorService {

	@Autowired
	RestTemplate template;
	
	public UserOrderDetailsDTO getUserOrderDetails(final String userId)
	{
		// 1. Get the user details from user service
		String url = "http://localhost:8083/users/"+userId;
		final UserDTO user = template.getForObject(url, UserDTO.class);
		
		// 2. Get the order details from order service
		url = "http://localhost:8081/order/user/"+userId;
		final ResponseEntity<List<OrderDTO>> orderResponse = template.exchange(url, HttpMethod.GET,null,new ParameterizedTypeReference<List<OrderDTO>>() {});
		final List<OrderDTO> orders = orderResponse.getBody();
		
		// 3. Prepare the final response object
		final UserOrderDetailsDTO response = new UserOrderDetailsDTO();
		response.setUser(user);
		response.setOrders(orders);
		
		return response;
	}
}
