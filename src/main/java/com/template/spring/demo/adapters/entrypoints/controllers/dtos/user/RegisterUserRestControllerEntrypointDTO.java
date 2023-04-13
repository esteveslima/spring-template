package com.template.spring.demo.adapters.entrypoints.controllers.dtos.user;

import com.template.spring.demo.infrastructure.annotations.validators.user.UserEmailFieldValidator;
import com.template.spring.demo.infrastructure.annotations.validators.user.UserPasswordFieldValidator;
import com.template.spring.demo.infrastructure.annotations.validators.user.UserUsernameFieldValidator;
import lombok.Data;

public class RegisterUserRestControllerEntrypointDTO {

    public class Request {
        @Data
        public static class Body {
            @UserUsernameFieldValidator
            public final String username;

            @UserEmailFieldValidator
            public final String email;

            @UserPasswordFieldValidator
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
