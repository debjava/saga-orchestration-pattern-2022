package com.ddlab.rnd.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ddlab.rnd.entity.CancelRequest;
import com.ddlab.rnd.entity.PaymentRequest;

@FeignClient(name = "PaymentServiceAPI", url = "${payment.service.api.url}")
public interface PaymentClient {

	@PostMapping(path = "/payment")
	ResponseEntity<String> makePaymentCall(@RequestBody PaymentRequest payRequest);
	
	@PostMapping(path = "/undopayment")
	ResponseEntity<String> reversePayment(@RequestBody CancelRequest cancelRequest);

}
