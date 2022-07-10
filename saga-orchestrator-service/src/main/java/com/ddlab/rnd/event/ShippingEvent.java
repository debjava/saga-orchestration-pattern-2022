package com.ddlab.rnd.event;

import lombok.Data;

@Data
public class ShippingEvent {
	private long orderId;
	private String itemName;
	private String actionName;
	private String shippingAdress;

}
