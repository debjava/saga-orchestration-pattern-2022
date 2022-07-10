package com.ddlab.rnd.listeners;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ddlab.rnd.event.OrderEvent;

@Component
public class OrderCompleteListener {

	@KafkaListener(topics = "${kafka.order.complete.topic.name}")
	public void listenOrder(OrderEvent orderEvent) {
		if (orderEvent.getActionName().equalsIgnoreCase("ORDER_COMPLETE")) {
			System.out.println("Your order has been successfully processed, you will receive in 2 days");
		}
	}
}
