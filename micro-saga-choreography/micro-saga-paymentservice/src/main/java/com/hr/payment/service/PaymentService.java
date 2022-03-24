package com.hr.payment.service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hr.common.dto.PaymentRequestDTO;
import com.hr.common.event.OrderEvent;
import com.hr.common.event.PaymentEvent;
import com.hr.common.event.PaymentStatus;
import com.hr.payment.entity.UserBalance;
import com.hr.payment.entity.UserTransaction;
import com.hr.payment.repository.UserBalanceRepository;
import com.hr.payment.repository.UserTransactionRepository;

/**
 * @author himanshubhusan.rath
 *
 */
@Service
public class PaymentService {

	@Autowired
	private UserBalanceRepository userBalanceRepo;

	@Autowired
	private UserTransactionRepository userTransactionRepo;

	/*
	 * Initializes data for some users
	 */
	@PostConstruct
	public void initUserBalanceRepo() {
		userBalanceRepo.saveAll(Stream.of(new UserBalance(1, 100), new UserBalance(2, 200), new UserBalance(3, 300))
				.collect(Collectors.toList()));
	}

	/**
	 * 
	 * 1. Find the balance for the user 
	 * 2. If sufficient balance is available, deduct the order amount, create a user balance transaction and create PaymentEvent with success status 
	 * 3. Else create a PaymentEvent with failed status
	 * 
	 * 
	 * @param orderEvent
	 * @return
	 */
	@Transactional
	public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
		
		final PaymentRequestDTO paymentReqDTO = new PaymentRequestDTO(orderEvent.getOrderRequestDTO().getUserID(),orderEvent.getOrderRequestDTO().getOrderID(),orderEvent.getOrderRequestDTO().getAmount());
		final int orderAmount = paymentReqDTO.getAmount();
		final int orderID = paymentReqDTO.getOrderID();
		final int userID = paymentReqDTO.getUserID();

		return userBalanceRepo.findById(userID)
						.filter(ub->ub.getPrice() >= orderAmount)
						.map(ub -> {
							ub.setPrice(ub.getPrice() - orderAmount);
							userBalanceRepo.save(ub);
							
							userTransactionRepo.save(new UserTransaction(orderID,userID,orderAmount));
							return new PaymentEvent(paymentReqDTO, PaymentStatus.PAYMENT_SUCCESS);
						})
						.orElse(new PaymentEvent(paymentReqDTO, PaymentStatus.PAYMENT_FAILED));
	}

	@Transactional
	public void cancelOrderEvent(OrderEvent orderEvent) {
		
	}

}
