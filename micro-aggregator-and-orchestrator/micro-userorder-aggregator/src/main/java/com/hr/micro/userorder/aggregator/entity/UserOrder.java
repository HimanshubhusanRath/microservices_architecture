package com.hr.micro.userorder.aggregator.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserOrder {

	@Id
	private Integer userID;
	private int orderCount;
	
}
