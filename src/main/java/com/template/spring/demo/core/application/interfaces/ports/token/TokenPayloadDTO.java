package com.template.spring.demo.core.application.interfaces.ports.token;

import com.template.spring.demo.core.domain.entities.UserEntity;


// Not using lombok due to the usage and limitations of ObjectMapper.convertValue
public class TokenPayloadDTO {
    public String username;
    public UserEntity.EnumUserRole role;

    public TokenPayloadDTO() {
    }

    public TokenPayloadDTO(String username, UserEntity.EnumUserRole role) {
        this.username = username;
        this.role = role;
    }
}
