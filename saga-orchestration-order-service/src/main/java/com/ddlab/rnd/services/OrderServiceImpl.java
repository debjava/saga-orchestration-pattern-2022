package com.ddlab.rnd.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Service;

import com.ddlab.rnd.entity.OrderEntity;
import com.ddlab.rnd.event.OrderEvent;

@Service
public class OrderServiceImpl {

	@Autowired
	private KafkaTemplate<String, OrderEvent> kafkaTemplate;

	@Value("${kafka.order.topic.name}")
	private String orderTopicName;

	public void placeOrder(OrderEntity order) {
		OrderEvent orderEvent = new OrderEvent();
		orderEvent.setOrderId(order.getOrderId());
		orderEvent.setOrderName(order.getOrderName());
		orderEvent.setActionName("ORDER_PLACED");
		orderEvent.setOrderPrice(order.getOrderPrice());
		orderEvent.setShipToAddress(order.getShipToAddress());

		// Publish
		kafkaTemplate.send(orderTopicName, orderEvent);
		System.out.println("Order sent to topic ...");
	}
}
