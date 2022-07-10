package com.ddlab.rnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddlab.rnd.entity.OrderEntity;
import com.ddlab.rnd.services.OrderServiceImpl;

@RestController
public class OrderController {
	
	@Autowired
	private OrderServiceImpl orderService;

	@PostMapping(path = "/order")
	public ResponseEntity<String> placeOrder(@RequestBody OrderEntity order) {
		orderService.placeOrder(order);
		String msg = "Order successfully placed ...";
		return new ResponseEntity<String>(msg, HttpStatus.CREATED);
	}
}
