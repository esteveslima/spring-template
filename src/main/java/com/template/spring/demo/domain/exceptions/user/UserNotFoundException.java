package com.template.spring.demo.domain.exceptions.user;

public class UserNotFoundException extends RuntimeException {
    public final String payload;

    public UserNotFoundException() {
        super();
        this.payload = null;
    }
    public UserNotFoundException(String payload) {
        super();
        this.payload = payload;
    }
}