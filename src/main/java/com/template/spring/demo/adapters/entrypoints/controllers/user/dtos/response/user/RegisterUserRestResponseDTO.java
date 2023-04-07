package com.template.spring.demo.adapters.entrypoints.controllers.user.dtos.response.user;

import lombok.Data;

@Data
public class RegisterUserRestResponseDTO {
    public final String username;
    public final String email;
    public final int id;
}
