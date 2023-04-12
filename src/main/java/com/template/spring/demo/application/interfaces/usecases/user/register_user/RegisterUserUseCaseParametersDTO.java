package com.template.spring.demo.application.interfaces.usecases.user.register_user;

import lombok.Data;

@Data
public class RegisterUserUseCaseParametersDTO {
    public final String username;
    public final String email;
    public final String password;
}
