package com.hr.micro.userorder.aggregator.config;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hr.common.event.OrderEvent;
import com.hr.micro.userorder.aggregator.entity.UserOrder;
import com.hr.micro.userorder.aggregator.repository.UserOrderRepository;


@Configuration
public class OrderCompletedEventConsumerConfig {

	@Autowired
	private UserOrderRepository userOrderRepo;
	
	@Bean
	public Consumer<OrderEvent> consumOrderCompleteEvent()
	{
		return (event) -> {
			System.out.println("Order Event in USER-ORDER module !!");
			final Integer userId = event.getOrderRequestDTO().getUserID();
			Optional<UserOrder> userOrder = userOrderRepo.findById(userId);
			
			if(userOrder.isPresent())
			{
				final UserOrder uo = userOrder.get();
				uo.setOrderCount(uo.getOrderCount()+1);
				userOrderRepo.save(uo);
			}
			else
			{
				final UserOrder uo = new UserOrder(userId, 1);
				userOrderRepo.save(uo);
			}
		};
	}
}
