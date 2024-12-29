package com.circuit.breaker.client.service;

import com.circuit.breaker.client.model.Payment;
import com.circuit.breaker.client.repo.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServices {
    private final PaymentRepository paymentRepository;

    @SneakyThrows
    @Transactional
    public Payment processPayment(Long orderId, double amount) {
        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .status("SUCCESS")
                .build();

        paymentRepository.save(payment);
        return payment;
    }

    public List<Payment> findAll(){
        return paymentRepository.findAll();
    }
}
