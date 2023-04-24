package com.template.spring.demo.core.domain.exceptions.product;

import java.util.Map;

public class ProductFieldFailedValidationException extends RuntimeException {
    public final Map<String, Object> payload;   // field/value failure map

    public ProductFieldFailedValidationException(String message) {
        super(message);
        this.payload = null;
    }
    public ProductFieldFailedValidationException(String message, Map<String, Object> payload) {
        super(message);
        this.payload = payload;
    }
}