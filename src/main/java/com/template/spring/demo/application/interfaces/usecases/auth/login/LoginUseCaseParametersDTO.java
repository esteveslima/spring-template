package com.template.spring.demo.application.interfaces.usecases.auth.login;

import lombok.Data;

@Data
public class LoginUseCaseParametersDTO {
    public final String username;
    public final String password;
}
