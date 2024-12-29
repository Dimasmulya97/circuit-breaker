package com.circuit.breaker.client.order_services.repo;

import com.circuit.breaker.client.order_services.model.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogHistoryRepository extends JpaRepository<LogHistory, Integer> {
}
