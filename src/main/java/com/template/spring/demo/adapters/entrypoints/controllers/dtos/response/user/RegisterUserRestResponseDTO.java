package com.template.spring.demo.adapters.entrypoints.controllers.dtos.response.user;

import lombok.Data;

@Data
public class RegisterUserRestResponseDTO {
    public final String username;
    public final String email;
    public final int id;
}
