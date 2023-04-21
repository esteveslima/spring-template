package com.template.spring.demo.core.application.interfaces.ports.token;

import com.template.spring.demo.core.application.exceptions.token.InvalidTokenException;

public interface TokenGateway {
    public TokenPayloadDTO decodeToken(String token) throws InvalidTokenException;
    public String generateToken(TokenPayloadDTO tokenPayload);
}
