package com.template.spring.demo.application.exceptions.auth;

import com.template.spring.demo.application.interfaces.usecases.auth.login.LoginUseCaseParametersDTO;

public class LoginUnauthorizedException extends RuntimeException {
    public final LoginUseCaseParametersDTO payload;

    public LoginUnauthorizedException() {
        super();
        this.payload = null;
    }
    public LoginUnauthorizedException(LoginUseCaseParametersDTO payload) {
        super();
        this.payload = payload;
    }
}