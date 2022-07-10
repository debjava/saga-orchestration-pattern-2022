package com.ddlab.rnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ddlab.rnd.entity.ItemRequestEntity;
import com.ddlab.rnd.event.CancelEvent;
import com.ddlab.rnd.event.PaymentEvent;

@Service
public class InventoryServiceImpl {

	@Autowired
	private KafkaTemplate<String, PaymentEvent> kafkaTemplate;

	@Autowired
	private KafkaTemplate<String, CancelEvent> cancelKafkaTemplate;

	@Value("${kafka.inventory.out.topic.name}")
	private String topicName;

	@Value("${kafka.order.cancel.topic.name}")
	private String cancelTopicName;

	private void sendForPaymentInitiation(PaymentEvent paymentEvent) {
		System.out.println("Initiating payment for the item");
		kafkaTemplate.send(topicName, paymentEvent);
		System.out.println("Sent for payment initiation ...");
	}
	
	public void initiatePayment(ItemRequestEntity itemRequest) {
		if(itemRequest.getActionName().equalsIgnoreCase("ITEM_AVAILABLE")) {
			boolean itemAvlable = isItemAvailable(itemRequest.getItemName());
			if(itemAvlable) {
				PaymentEvent paymentEvent = new PaymentEvent();
				paymentEvent.setOrderId(itemRequest.getOrderId());
				paymentEvent.setItemName(itemRequest.getItemName());
				paymentEvent.setPrice(itemRequest.getPrice());
				paymentEvent.setActionName("INITIATE_PAYMENT");
				paymentEvent.setShipToAddress(itemRequest.getShipToAddress());
				
				sendForPaymentInitiation(paymentEvent);
			} else {
				CancelEvent cancelEvent = new CancelEvent();
				cancelEvent.setActionName("CANCEL_ORDER");
				cancelEvent.setReason("Outof Stock");
				
				System.out.println("Order has been cancelled and item is put back in the inventory");
				cancelKafkaTemplate.send(cancelTopicName, cancelEvent);
			}
		}
	}
	
	private boolean isItemAvailable(String name) {
		if(name.startsWith("Vivo")) return false;
		
		return true;
	}

}
