package com.ddlab.rnd.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ddlab.rnd.clients.PaymentClient;
import com.ddlab.rnd.entity.PaymentRequest;
import com.ddlab.rnd.event.PaymentEvent;

@Component
public class InventoryListener {
	
	@Autowired
	private PaymentClient payClient;

	@KafkaListener(topics = "${kafka.inventory.out.topic.name}")
	public void receivePayment(PaymentEvent paymentEvent) {
		if (paymentEvent.getActionName().equals("INITIATE_PAYMENT")) {
			
			PaymentRequest payRequest = new PaymentRequest();
			payRequest.setActionName("RECEIVE_PAYMENT");
			payRequest.setItemName(paymentEvent.getItemName());
			payRequest.setOrderId(paymentEvent.getOrderId());
			payRequest.setPrice(paymentEvent.getPrice());
			payRequest.setShippingAdress(paymentEvent.getShipToAddress());
			payClient.makePaymentCall(payRequest);
		}
	}
}
