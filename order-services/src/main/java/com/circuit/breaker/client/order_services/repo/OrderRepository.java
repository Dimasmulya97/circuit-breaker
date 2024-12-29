package com.circuit.breaker.client.order_services.repo;

import com.circuit.breaker.client.order_services.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
