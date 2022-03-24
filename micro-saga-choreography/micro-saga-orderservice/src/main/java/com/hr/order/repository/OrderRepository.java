package com.hr.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.order.entity.PurchaseOrder;

public interface OrderRepository extends JpaRepository<PurchaseOrder, Integer>{

}
