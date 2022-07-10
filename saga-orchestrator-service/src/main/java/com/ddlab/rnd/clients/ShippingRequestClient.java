package com.ddlab.rnd.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.ddlab.rnd.entity.ShipRequest;

@FeignClient(name = "ShipmentServiceAPI", url = "${shipping.service.api.url}")
public interface ShippingRequestClient {

	@PostMapping("/ship")
	public ResponseEntity<String> shipProduct(ShipRequest shipRequest);
}
