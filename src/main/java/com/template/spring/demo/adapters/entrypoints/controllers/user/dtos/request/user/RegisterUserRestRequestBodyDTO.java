package com.template.spring.demo.adapters.entrypoints.controllers.user.dtos.request.user;

import com.template.spring.demo.infrastructure.validators.user.UserEmailFieldValidator;
import com.template.spring.demo.infrastructure.validators.user.UserPasswordFieldValidator;
import com.template.spring.demo.infrastructure.validators.user.UserUsernameFieldValidator;
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
