package com.ddlab.rnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ddlab.rnd.entity.ShipRequest;
import com.ddlab.rnd.event.CancelEvent;
import com.ddlab.rnd.event.OrderEvent;

@Service
public class ShippingServiceImpl {
	
	@Autowired
	private KafkaTemplate<String, OrderEvent> kafkaTemplate;
	
	@Value("${kafka.order.complete.topic.name}")
	private String topicName;
	
	@Autowired
	private KafkaTemplate<String, CancelEvent> cancelKafkaTemplate;
	
	@Value("${kafka.cancel.ship.topic.name}")
	private String cancelTopicName;
	
	public void cancelOrder(CancelEvent cancelEvent) {
		cancelKafkaTemplate.send(cancelTopicName, cancelEvent);
		System.out.println("Your order processing has been cancelled due to invalid address");
	}
	
	public void shipItem(ShipRequest shipRequest) {
		boolean invalidShipAdres = shipRequest.getShippingAdress().startsWith("Invalid");
		System.out.println("invalidShipAdres : " + invalidShipAdres);
		boolean validAction = shipRequest.getActionName().equalsIgnoreCase("SHIPPED_COMPLETE");
		if (validAction && !invalidShipAdres) {
			OrderEvent orderEvent = new OrderEvent();
			orderEvent.setOrderId(shipRequest.getOrderId());
			orderEvent.setActionName("ORDER_COMPLETE");
			
			kafkaTemplate.send(topicName, orderEvent);
			System.out.println("Your order processing has been complete");
			
		} else {
			CancelEvent cancelEvent = new CancelEvent();
			cancelEvent.setActionName("CANCEL_PAYMENT");
			cancelEvent.setOrderId(shipRequest.getOrderId());
			cancelEvent.setOrderName(shipRequest.getItemName());
			cancelEvent.setReason("This item does not ship to address: " + shipRequest.getShippingAdress());
			cancelKafkaTemplate.send(cancelTopicName, cancelEvent);
			System.out.println("Your order processing has been cancelled due to invalid address");
		}
	}
}
