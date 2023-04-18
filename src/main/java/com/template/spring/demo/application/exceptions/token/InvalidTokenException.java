package com.template.spring.demo.application.exceptions.token;

import com.template.spring.demo.application.interfaces.dtos.usecases.auth.LoginUseCaseDTO;

public class InvalidTokenException extends RuntimeException {
    public final String payload;

    public InvalidTokenException() {
        super();
        this.payload = null;
    }
    public InvalidTokenException(String payload) {
        super();
        this.payload = payload;
    }

    public InvalidTokenException(String payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}