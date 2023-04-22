package com.template.spring.demo.core.application.interfaces.dtos.usecases.auth;

import lombok.Data;

public class LoginStoreUseCaseDTO {

    @Data
    public static class Params {
        public final String username;
        public final String password;
    }

    @Data
    public static class Result {
        public final String token;
    }
}
