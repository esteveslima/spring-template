package com.template.spring.demo.domain.repositories.user.dtos;

import lombok.Data;

public class UserGatewayRegisterUserDTO {
    @Data
    public static class Params {
        public final String username;
        public final String email;
        public final String password;
    }

    @Data
    public static class Result {
        public final int id;
        public final String username;
        public final String email;
    }

}
