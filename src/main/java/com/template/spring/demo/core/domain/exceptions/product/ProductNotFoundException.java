package com.template.spring.demo.core.domain.exceptions.product;

public class ProductNotFoundException extends RuntimeException {
    public final Object payload;

    public ProductNotFoundException() {
        super();
        this.payload = null;
    }
    public ProductNotFoundException(String payload) {
        super();
        this.payload = payload;
    }

    public ProductNotFoundException(String payload, Throwable cause) {
        super(cause);
        this.payload = payload;
    }
}