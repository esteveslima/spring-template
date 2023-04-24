package com.template.spring.demo.core.domain.exceptions.store;

// TODO: make all custom exceptions checked exceptions so they are handled at the controller level with a proper response
// TODO: create iterface for usecases(?)
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