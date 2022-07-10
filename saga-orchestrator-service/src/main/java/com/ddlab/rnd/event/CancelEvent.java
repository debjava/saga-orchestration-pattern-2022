package com.ddlab.rnd.event;

import lombok.Data;

@Data
public class CancelEvent {
	private long orderId;
	private String actionName;
	private String orderName;
	private String reason;
}
