package com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.user;

import com.template.spring.demo.external.infrastructure.annotations.validators.UserEntityFieldValidator;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

public class RegisterUserRestControllerEntrypointDTO {

    public class Request {
        @Data
        public static class Body {
            @UserEntityFieldValidator(fieldName = "username")
            public String username;

            @Email
            @UserEntityFieldValidator(fieldName = "email")
            public String email;

            @UserEntityFieldValidator(fieldName = "password")
            public String password;
        }
    }

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public String username;
            public String email;
            public int id;
        }
    }

}
