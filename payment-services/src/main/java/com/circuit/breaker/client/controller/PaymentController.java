package com.circuit.breaker.client.controller;

import com.circuit.breaker.client.dto.PaymentRequest;
import com.circuit.breaker.client.model.Payment;
import com.circuit.breaker.client.service.PaymentServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentServices paymentService;

    @PostMapping("/create")
    public Payment processPayment(@RequestBody PaymentRequest paymentRequest) {
           return paymentService.processPayment(paymentRequest.getOrderId(),paymentRequest.getAmount());
    }

    @GetMapping("/list")
    public List<Payment> findAll() {
        return paymentService.findAll();
    }
}
