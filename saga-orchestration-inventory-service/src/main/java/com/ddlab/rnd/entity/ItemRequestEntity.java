package com.ddlab.rnd.entity;

import lombok.Data;

@Data
public class ItemRequestEntity {

	private long orderId;
	private String itemName;
	private int price;
	private String actionName;
	private String shipToAddress;
}
