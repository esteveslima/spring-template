package com.template.spring.demo.application.usecases.register_user;

import lombok.Data;

@Data
public class RegisterUserUseCaseParametersDTO {
    public final String username;
    public final String email;
    public final String password;
}
