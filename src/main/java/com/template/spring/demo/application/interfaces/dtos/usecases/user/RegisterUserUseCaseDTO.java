package com.template.spring.demo.application.interfaces.dtos.usecases.user;

import lombok.Data;

public class RegisterUserUseCaseDTO {
    @Data
    public static class Params {
        public final String username;
        public final String email;
        public final String password;
    }

    @Data
    public static class Result {
        public final String username;
        public final String email;
        public final int id;
    }
}
