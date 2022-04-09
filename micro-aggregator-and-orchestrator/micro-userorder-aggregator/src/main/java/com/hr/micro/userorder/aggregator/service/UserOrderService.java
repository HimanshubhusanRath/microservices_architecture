package com.hr.micro.userorder.aggregator.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.micro.userorder.aggregator.dto.UserOrderDTO;
import com.hr.micro.userorder.aggregator.entity.UserOrder;
import com.hr.micro.userorder.aggregator.repository.UserOrderRepository;

@Service
public class UserOrderService {

	@Autowired
	private UserOrderRepository repository;
	
	public UserOrderDTO getUserOrderDetails(final Integer userId)
	{
		final Optional<UserOrder> result = repository.findById(userId);
		if(result.isPresent())
		{
			final UserOrderDTO userOrderDTO = new UserOrderDTO();
			BeanUtils.copyProperties(result.get(), userOrderDTO);
			return userOrderDTO;
		}
		return null;
	}
}
