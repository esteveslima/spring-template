package com.template.spring.demo.core.application.exceptions.usecases.product;

public class ProductNotFromUserStoreException extends RuntimeException {
    public final Object payload;

    public ProductNotFromUserStoreException() {
        super();
        this.payload = null;
    }
    public ProductNotFromUserStoreException(Object payload) {
        super();
        this.payload = payload;
    }

    public ProductNotFromUserStoreException(Object payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}