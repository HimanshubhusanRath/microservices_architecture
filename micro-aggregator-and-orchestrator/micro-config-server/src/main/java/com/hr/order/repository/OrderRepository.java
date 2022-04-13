package com.hr.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.order.entity.PurchaseOrder;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer>{

	List<PurchaseOrder> findByUserId(final Integer userId);

}
