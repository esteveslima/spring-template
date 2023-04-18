package com.template.spring.demo.application.interfaces.ports.token;

import com.template.spring.demo.domain.entities.UserEntity;


// Not using lombok due to the usage and limitations of ObjectMapper.convertValue
public class TokenPayloadDTO {
    public String username;
    public UserEntity.EnumUserRole role;

    public TokenPayloadDTO() {
    }

    public TokenPayloadDTO(String username, String email, UserEntity.EnumUserRole roles) {
        this.username = username;
        this.role = roles;
    }
}
