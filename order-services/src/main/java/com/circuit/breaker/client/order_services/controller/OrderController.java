package com.circuit.breaker.client.order_services.controller;

import com.circuit.breaker.client.order_services.dto.Response;
import com.circuit.breaker.client.order_services.model.LogHistory;
import com.circuit.breaker.client.order_services.model.Order;
import com.circuit.breaker.client.order_services.repo.LogHistoryRepository;
import com.circuit.breaker.client.order_services.service.OrderServices;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/order")
@Slf4j
@RequiredArgsConstructor
public class OrderController {
    private final OrderServices orderServices;
    private final LogHistoryRepository logHistoryRepository;


    @PostMapping("/create")
    @CircuitBreaker(name = "paymentServices", fallbackMethod = "placeOrderFallback")
    @Retry(name = "paymentServices")
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        log.info("Placing Order");
        return new ResponseEntity<>(orderServices.placeOrder(order),HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<Order> getAllOrders() {
        return orderServices.getAllOrders();
    }

    public ResponseEntity<String> placeOrderFallback(Order order, Throwable throwable) {
        log.error("Payment service is unavailable, reason: {}", throwable.getMessage());

        String message = "Oops! Something went wrong, please order after some time!";
        LogHistory logHistory = LogHistory.builder()
                .servicesName("payment-services")
                .endpoint("http://PAYMENT-SERVICES/payment/create")
                .errorMessage(message)
                .build();
        logHistoryRepository.save(logHistory);
        log.info("Cannot Place Order Executing Fallback logic");
        return new ResponseEntity<>(message,HttpStatus.SERVICE_UNAVAILABLE);
    }
}
