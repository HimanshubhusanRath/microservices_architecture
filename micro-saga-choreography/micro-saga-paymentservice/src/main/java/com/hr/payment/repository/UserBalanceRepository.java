package com.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.payment.entity.UserBalance;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Integer>{

}
