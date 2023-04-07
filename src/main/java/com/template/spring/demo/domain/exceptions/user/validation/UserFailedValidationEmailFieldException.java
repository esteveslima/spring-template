package com.template.spring.demo.domain.exceptions.user.validation;

public class UserFailedValidationEmailFieldException extends Exception {
    public final String payload;

    public UserFailedValidationEmailFieldException(String message) {
        super(message);
        this.payload = null;
    }
    public UserFailedValidationEmailFieldException(String message, String payload) {
        super(message);
        this.payload = payload;
    }
}