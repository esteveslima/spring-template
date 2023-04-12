package com.template.spring.demo.domain.exceptions.user;

public class UserAlreadyExistsException extends RuntimeException {
    public final String payload;

    public UserAlreadyExistsException() {
        super();
        this.payload = null;
    }
    public UserAlreadyExistsException(String payload) {
        super();
        this.payload = payload;
    }

}