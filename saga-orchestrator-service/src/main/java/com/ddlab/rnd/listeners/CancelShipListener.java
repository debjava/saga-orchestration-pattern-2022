package com.ddlab.rnd.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ddlab.rnd.clients.PaymentClient;
import com.ddlab.rnd.entity.CancelRequest;
import com.ddlab.rnd.event.CancelEvent;

@Component
public class CancelShipListener {
	
	@Autowired
	private PaymentClient payClient;
	
	@KafkaListener(topics = "${kafka.cancel.ship.topic.name}")
	public void listen(CancelEvent cancelEvent) {
		
		if(cancelEvent.getActionName().equals("CANCEL_PAYMENT")) {
			
			CancelRequest cancelReq = new CancelRequest();
			cancelReq.setActionName("CANCEL_PAYMENT");
			cancelReq.setOrderId(cancelEvent.getOrderId());
			cancelReq.setOrderName(cancelEvent.getOrderName());
			cancelReq.setReason(cancelEvent.getReason());
			
			payClient.reversePayment(cancelReq);
		}
	}
}
