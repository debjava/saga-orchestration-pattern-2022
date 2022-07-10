package com.ddlab.rnd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class SagaOrchestratorApp {

	public static void main(String[] args) {
		SpringApplication.run(SagaOrchestratorApp.class, args);
	}

}
