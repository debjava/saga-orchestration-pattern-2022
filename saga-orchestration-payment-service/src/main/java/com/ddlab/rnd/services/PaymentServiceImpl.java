package com.ddlab.rnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ddlab.rnd.entity.CancelRequest;
import com.ddlab.rnd.entity.PaymentRequest;
import com.ddlab.rnd.event.CancelEvent;
import com.ddlab.rnd.event.ShippingEvent;

@Service
public class PaymentServiceImpl {

	@Autowired
	private KafkaTemplate<String, ShippingEvent> kafkaTemplate;

	@Value("${kafka.shipping.out.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, CancelEvent> cancelKafkaTemplate;

	@Value("${kafka.payment.cancel.topic.name}")
	private String cancelTopicName;

	public void reverseTransaction(CancelRequest cancelRequest) {
		System.out.println("CancelRequest--->"+cancelRequest);
		System.out.println("Your transaction has been reversed, you will get back your amount in 48 hours...");
	}
	
	public void processPaymentRequest(PaymentRequest payRequest) {
		if(payRequest.getActionName().equals("RECEIVE_PAYMENT")) {
			boolean isReceived = isPaymentReceived(payRequest.getPrice());
			if (isReceived) {
				ShippingEvent shipEvent = new ShippingEvent();
				shipEvent.setActionName("SHIP_ITEM_ADDRESS");
				shipEvent.setItemName(payRequest.getItemName());
				shipEvent.setOrderId(payRequest.getOrderId());
				shipEvent.setShippingAdress(payRequest.getShippingAdress());
				
				kafkaTemplate.send(topicName, shipEvent);
				
			} else {
				// Insufficient balance
				CancelEvent cancelEvent = new CancelEvent();
				cancelEvent.setOrderId(payRequest.getOrderId());
				cancelEvent.setActionName("CANCEL_ORDER");
				cancelEvent.setReason("Insufficient fund...");
				cancelEvent.setOrderName(payRequest.getItemName());
				
				cancelKafkaTemplate.send(cancelTopicName, cancelEvent);
			}
		}
	}
	
	public boolean isPaymentReceived(int amount) {
		if (amount > 50000)
			return false;
		else
			return true;
	}

}
