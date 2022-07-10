package com.ddlab.rnd.entity;

import lombok.Data;

@Data
public class CancelRequest {
	private long orderId;
	private String actionName;
	private String orderName;
	private String reason;
}
