package com.ddlab.rnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddlab.rnd.entity.ShipRequest;
import com.ddlab.rnd.services.ShippingServiceImpl;

@RestController
public class ShippingController {
	
	@Autowired
	private ShippingServiceImpl shippingService;
	
	@PostMapping("/ship")
	public ResponseEntity<String> shipItem(@RequestBody ShipRequest shipRequest) {
		System.out.println("ShipRequest---->"+shipRequest);
		shippingService.shipItem(shipRequest);
		return new ResponseEntity<String>("Package has been shipped...", HttpStatus.CREATED);
	}

}
