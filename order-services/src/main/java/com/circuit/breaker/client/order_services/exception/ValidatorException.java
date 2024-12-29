package com.circuit.breaker.client.order_services.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidatorException extends Exception {
    private String message;
    private String value;

    public ValidatorException(String message) {
        this.message = message;
    }
}
