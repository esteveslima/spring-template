package com.template.spring.demo.domain.repositories.user.register_user;

import lombok.Data;

@Data
public class UserGatewayRegisterUserResultDTO {
    public final int id;
    public final String username;
    public final String email;
}
