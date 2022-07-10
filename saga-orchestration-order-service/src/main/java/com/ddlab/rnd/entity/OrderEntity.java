package com.ddlab.rnd.entity;

import lombok.Data;

@Data
public class OrderEntity {
	private long orderId;
	private String orderName;
	private int orderPrice;
	private String shipToAddress;
}
