package com.template.spring.demo.domain.exceptions.user.validations;

public class UserFailedValidationPasswordFieldException extends Exception {
    public final String payload;

    public UserFailedValidationPasswordFieldException(String message) {
        super(message);
        this.payload = null;
    }
    public UserFailedValidationPasswordFieldException(String message, String payload) {
        super(message);
        this.payload = payload;
    }
}