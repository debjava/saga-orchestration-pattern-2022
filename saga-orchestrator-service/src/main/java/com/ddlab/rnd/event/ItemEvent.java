package com.ddlab.rnd.event;

import lombok.Data;

@Data
public class ItemEvent {
	
	private long orderId;
	private String itemName;
	private int price;
	private String actionName;
	private String shipToAddress;

}
