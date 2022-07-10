package com.ddlab.rnd.entity;

import lombok.Data;

@Data
public class ItemRequest {

	private long orderId;
	private String itemName;
	private int price;
	private String actionName;
	private String shipToAddress;
}
