package com.hr.micro.userorder.aggregator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hr.micro.userorder.aggregator.entity.UserOrder;

@Repository
public interface UserOrderRepository extends JpaRepository<UserOrder, Integer>{

}
