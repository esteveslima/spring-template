package com.template.spring.demo.core.domain.exceptions.store;

public class StoreNotFoundException extends RuntimeException {
    public final Object payload;

    public StoreNotFoundException() {
        super();
        this.payload = null;
    }
    public StoreNotFoundException(Object payload) {
        super();
        this.payload = payload;
    }

    public StoreNotFoundException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}