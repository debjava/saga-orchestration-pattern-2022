package com.ddlab.rnd.listeners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.ddlab.rnd.clients.ShippingRequestClient;
import com.ddlab.rnd.entity.ShipRequest;
import com.ddlab.rnd.event.ShippingEvent;

@Component
public class ShippingListener {
	
	@Autowired
	private ShippingRequestClient shipClient;

	@KafkaListener(topics = "${kafka.shipping.out.topic.name}")
	public void receiveItemForShipping(ShippingEvent shipEvent) {
		System.out.println("ShippingEvent---->"+shipEvent);
		
		//Make a call to Shipping API
		ShipRequest shipReq = new ShipRequest();
		shipReq.setActionName("SHIPPED_COMPLETE");
		shipReq.setItemName(shipEvent.getItemName());
		shipReq.setOrderId(shipEvent.getOrderId());
		shipReq.setShippingAdress(shipEvent.getShippingAdress());
		shipClient.shipProduct(shipReq);
		
	}

}
