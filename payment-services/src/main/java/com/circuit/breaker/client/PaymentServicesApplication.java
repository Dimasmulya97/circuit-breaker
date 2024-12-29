package com.circuit.breaker.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentServicesApplication.class, args);
	}


}
