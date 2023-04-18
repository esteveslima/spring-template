package com.template.spring.demo.application.exceptions.auth;

public class UnauthorizedException extends RuntimeException {
    public final Object payload;

    public UnauthorizedException() {
        super();
        this.payload = null;
    }
    public UnauthorizedException(Object payload) {
        super();
        this.payload = payload;
    }

    public UnauthorizedException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}