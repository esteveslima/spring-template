package com.template.spring.demo.adapters.entrypoints.controllers.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class LoginRestControllerEntrypointDTO {

    public class Request {
        @Data
        public static class Body {
            @NotBlank
            public final String username;

            @NotBlank
            public final String password;
        }
    }

    public class Response {
        @Data
        public static class Body {
            public final String authToken;
        }
    }

}
