package com.ddlab.rnd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ddlab.rnd.entity.ItemRequestEntity;
import com.ddlab.rnd.services.InventoryServiceImpl;

@RestController
public class InventoryController {
	
	@Autowired
	private InventoryServiceImpl inventoryService;
	
	@PostMapping(path = "/inventory/item")
	public ResponseEntity<String> processInventory(@RequestBody ItemRequestEntity itemRequest) {
		System.out.println("Received request for : "+itemRequest);
		inventoryService.initiatePayment(itemRequest);
		return new ResponseEntity<String>("Item Received for processing", HttpStatus.CREATED);
	}
}
