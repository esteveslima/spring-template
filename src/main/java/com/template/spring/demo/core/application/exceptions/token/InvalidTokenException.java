package com.template.spring.demo.core.application.exceptions.token;

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