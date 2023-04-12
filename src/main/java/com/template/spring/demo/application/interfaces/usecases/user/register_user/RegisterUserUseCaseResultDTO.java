package com.template.spring.demo.application.interfaces.usecases.user.register_user;

import lombok.Data;

@Data
public class RegisterUserUseCaseResultDTO {
    public final String username;
    public final String email;
    public final int id;
}