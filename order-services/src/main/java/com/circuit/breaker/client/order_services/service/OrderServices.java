package com.circuit.breaker.client.order_services.service;

import com.circuit.breaker.client.order_services.dto.PaymentRequest;
import com.circuit.breaker.client.order_services.dto.PaymentResponse;
import com.circuit.breaker.client.order_services.dto.Response;
import com.circuit.breaker.client.order_services.exception.NotFoundException;
import com.circuit.breaker.client.order_services.model.LogHistory;
import com.circuit.breaker.client.order_services.model.Order;
import com.circuit.breaker.client.order_services.repo.LogHistoryRepository;
import com.circuit.breaker.client.order_services.repo.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServices {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;
    private final LogHistoryRepository logHistoryRepository;
    private final String ERROR_PAYMENT = "Service Unavailable, please try again later";

    @Transactional
    public String  placeOrder(Order order) {
        try {
            order.setStatus("PENDING");
            orderRepository.save(order);

            log.info("Calling payment service for order: {}", order.getId());

            // Call payment service
            PaymentRequest paymentRequest = new PaymentRequest(order.getId(), order.getHarga());
            PaymentResponse paymentResponse = restTemplate.postForObject(
                    "http://PAYMENT-SERVICES/payment/create",
                    paymentRequest,
                    PaymentResponse.class
            );

            log.info("Payment response received: {}", paymentResponse);
            // Update order status based on payment response
            order.setStatus("SUCCESS".equals(paymentResponse.getStatus()) ? "COMPLETED" : "FAILED");
            orderRepository.save(order);
            return String.valueOf(order);
        } catch (Exception e) {
            log.error("Exception caught in placeOrder method: {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }

    private NotFoundException notFoundException (String message){
        return new NotFoundException(message);
    }

    private BadRequestException badRequestException (String message){
        return new BadRequestException(message);
    }

    private RuntimeException runtimeException (String message){
        return new RuntimeException(message);
    }
}
