package com.template.spring.demo.core.application.interfaces.auth_token;


// Not using lombok due to the usage and limitations of ObjectMapper.convertValue
public class AuthTokenPayloadDTO {
    public int userId;
    public AuthRoleEnum role;

    public enum AuthRoleEnum {
        USER,
        STORE;

        public class MapValue {
            public static final String USER = "USER";
            public static final String STORE = "STORE";
        }
    }

    public AuthTokenPayloadDTO() {
    }

    public AuthTokenPayloadDTO(int userId, AuthRoleEnum role) {
        this.userId = userId;
        this.role = role;
    }
}
