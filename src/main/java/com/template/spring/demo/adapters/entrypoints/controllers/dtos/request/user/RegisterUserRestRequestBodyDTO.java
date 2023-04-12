package com.template.spring.demo.adapters.entrypoints.controllers.dtos.request.user;

import com.template.spring.demo.infrastructure.annotations.validators.user.UserEmailFieldValidator;
import com.template.spring.demo.infrastructure.annotations.validators.user.UserPasswordFieldValidator;
import com.template.spring.demo.infrastructure.annotations.validators.user.UserUsernameFieldValidator;
import lombok.Data;

@Data
public class RegisterUserRestRequestBodyDTO {
    @UserUsernameFieldValidator
    public final String username;

    @UserEmailFieldValidator
    public final String email;

    @UserPasswordFieldValidator
    public final String password;
}
