package com.template.spring.demo.core.domain.exceptions.store;

public class StoreNotFoundException extends RuntimeException {
    public final Object payload;

    public StoreNotFoundException() {
        super();
        this.payload = null;
    }
    public StoreNotFoundException(String payload) {
        super();
        this.payload = payload;
    }

    public StoreNotFoundException(String payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}