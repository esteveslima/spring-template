package com.template.spring.demo.core.domain.exceptions.store;

import java.util.Map;

public class StoreFieldFailedValidationException extends RuntimeException {
    public final Map<String, Object> payload;   // field/value failure map

    public StoreFieldFailedValidationException(String message) {
        super(message);
        this.payload = null;
    }
    public StoreFieldFailedValidationException(String message, Map<String, Object> payload) {
        super(message);
        this.payload = payload;
    }
}