package com.template.spring.demo.adapters.entrypoints.controllers.dtos.user;

import com.template.spring.demo.infrastructure.annotations.validators.UserEntityFieldValidator;
import lombok.Data;

public class RegisterUserRestControllerEntrypointDTO {

    public class Request {
        @Data
        public static class Body {
            @UserEntityFieldValidator(fieldName = "username")
            public final String username;

            @UserEntityFieldValidator(fieldName = "email")
            public final String email;

            @UserEntityFieldValidator(fieldName = "password")
            public final String password;
        }
    }

    public class Response {
        @Data
        public static class Body {
            public final String username;
            public final String email;
            public final int id;
        }
    }

}
