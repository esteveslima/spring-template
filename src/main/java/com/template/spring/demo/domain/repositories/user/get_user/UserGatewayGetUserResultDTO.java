package com.template.spring.demo.domain.repositories.user.get_user;

import lombok.Data;

@Data
public class UserGatewayGetUserResultDTO {
    public final int id;
    public final String username;
    public final String email;
    public final String hashPassword;
}
