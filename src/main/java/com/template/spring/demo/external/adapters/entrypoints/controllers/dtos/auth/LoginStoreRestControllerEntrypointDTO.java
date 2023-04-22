package com.template.spring.demo.external.adapters.entrypoints.controllers.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

public class LoginStoreRestControllerEntrypointDTO {

    public class Request {
        @Data
        public static class Body {
            @NotBlank
            public String username;

            @NotBlank
            public String password;
        }
    }

    public class Response {
        @Data
        @AllArgsConstructor
        public static class Body {
            public String token;
        }
    }

}
