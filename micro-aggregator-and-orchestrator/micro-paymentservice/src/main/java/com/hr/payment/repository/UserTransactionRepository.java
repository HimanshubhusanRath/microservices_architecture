package com.hr.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hr.payment.entity.UserTransaction;

public interface UserTransactionRepository extends JpaRepository<UserTransaction, Integer>{

}
