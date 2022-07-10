package com.ddlab.rnd.event;

import lombok.Data;

@Data
public class OrderEvent {
	private long orderId;
	private String actionName;
	private String orderName;
	private int orderPrice;
	private String shipToAddress;
}
