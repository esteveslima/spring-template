package com.template.spring.demo.application.exceptions.auth;

import com.template.spring.demo.application.interfaces.dtos.usecases.auth.LoginUseCaseDTO;

public class LoginUnauthorizedException extends RuntimeException {
    public final LoginUseCaseDTO.Params payload;

    public LoginUnauthorizedException() {
        super();
        this.payload = null;
    }
    public LoginUnauthorizedException(LoginUseCaseDTO.Params payload) {
        super();
        this.payload = payload;
    }
}