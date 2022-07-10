package com.ddlab.rnd.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ddlab.rnd.entity.ItemRequest;

@FeignClient(name = "InventoryServiceAPI", url = "${inventory.service.api.url}")
public interface InventoryRequestClient {

	@PostMapping("/inventory/item")
	public ResponseEntity<String> makeInventoryCall(@RequestBody ItemRequest itemRequest);

}
