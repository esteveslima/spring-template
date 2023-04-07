package com.template.spring.demo.domain.exceptions.user.validation;


public class UserFailedValidationUsernameFieldException extends Exception {
    public final String payload;

    public UserFailedValidationUsernameFieldException(String message) {
        super(message);
        this.payload = null;
    }
    public UserFailedValidationUsernameFieldException(String message, String payload) {
        super(message);
        this.payload = payload;
    }
}