package com.circuit.breaker.client.repo;

import com.circuit.breaker.client.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
