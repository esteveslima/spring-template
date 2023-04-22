package com.template.spring.demo.core.application.interfaces.auth;


// Not using lombok due to the usage and limitations of ObjectMapper.convertValue
public class AuthTokenPayloadDTO {
    public int id;
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

    public AuthTokenPayloadDTO(int id, AuthRoleEnum role) {
        this.id = id;
        this.role = role;
    }
}
