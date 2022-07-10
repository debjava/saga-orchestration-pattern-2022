package com.ddlab.rnd.entity;

import lombok.Data;

@Data
public class PaymentRequest {
	
	private long orderId;
	private String itemName;
	private String actionName;
	private String shippingAdress;
	private int price;

}
