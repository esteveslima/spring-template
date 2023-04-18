package com.template.spring.demo.domain.exceptions.user;

import java.util.Map;

public class UserFieldFailedValidationException extends Exception {
    public final Map<String, Object> payload;   // field/value failure map

    public UserFieldFailedValidationException(String message) {
        super(message);
        this.payload = null;
    }
    public UserFieldFailedValidationException(String message, Map<String, Object> payload) {
        super(message);
        this.payload = payload;
    }
}