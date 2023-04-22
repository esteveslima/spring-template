package com.template.spring.demo.core.domain.exceptions.store;

public class StoreAlreadyExistsException extends RuntimeException {
    public final Object payload;

    public StoreAlreadyExistsException() {
        super();
        this.payload = null;
    }
    public StoreAlreadyExistsException(Object payload) {
        super();
        this.payload = payload;
    }

    public StoreAlreadyExistsException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }

}