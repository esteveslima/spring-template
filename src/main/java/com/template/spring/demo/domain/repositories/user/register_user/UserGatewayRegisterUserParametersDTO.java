package com.template.spring.demo.domain.repositories.user.register_user;

import lombok.Data;

@Data
public class UserGatewayRegisterUserParametersDTO {
    public final String username;
    public final String email;
    public final String password;
}
