package com.template.spring.demo.adapters.entrypoints.controllers.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRestRequestBodyDTO {
    @NotBlank
    public final String username;

    @NotBlank
    public final String password;
}
