package com.ddlab.rnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddlab.rnd.entity.CancelRequest;
import com.ddlab.rnd.entity.PaymentRequest;
import com.ddlab.rnd.services.PaymentServiceImpl;

@RestController
public class PaymentController {
	
	@Autowired
	private PaymentServiceImpl payService;
	
	@PostMapping(path = "/payment")
	public ResponseEntity<String> initiatePayment(@RequestBody PaymentRequest payRequest) {
		System.out.println("Received request for payment: "+payRequest);
		payService.processPaymentRequest(payRequest);
		return new ResponseEntity<String>("Received Payment Request", HttpStatus.CREATED);
	}
	
	@PostMapping(path = "/undopayment")
	public ResponseEntity<String> reversePayment(@RequestBody CancelRequest cancelRequest) {
		System.out.println("Received request for payment: "+cancelRequest);
		payService.reverseTransaction(cancelRequest);
		return new ResponseEntity<String>("Received Payment Request", HttpStatus.CREATED);
	}

}
