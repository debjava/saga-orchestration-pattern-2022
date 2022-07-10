package com.ddlab.rnd.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ddlab.rnd.clients.InventoryRequestClient;
import com.ddlab.rnd.entity.ItemRequest;
import com.ddlab.rnd.event.OrderEvent;

@Component
public class PlacedOrderListener {
	
	@Autowired
	private InventoryRequestClient inventoryClient;

	@KafkaListener(topics = "${kafka.order.topic.name}",groupId = "saga-order-grp-id")
	public void listenOrderPlaced(OrderEvent orderEvent) {
		System.out.println("Order Details:" + orderEvent);
		if(orderEvent.getActionName().equalsIgnoreCase("ORDER_PLACED")) {
			
			// Make a call to inventory service
			ItemRequest itemRequest = new ItemRequest();
			
			itemRequest.setOrderId(orderEvent.getOrderId());
			itemRequest.setItemName(orderEvent.getOrderName());
			itemRequest.setPrice(orderEvent.getOrderPrice());
			itemRequest.setActionName("ITEM_AVAILABLE");
			itemRequest.setShipToAddress(orderEvent.getShipToAddress());
			
			inventoryClient.makeInventoryCall(itemRequest);
		}
	}
}
