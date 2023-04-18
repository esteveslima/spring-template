package com.template.spring.demo.application.interfaces.ports.token;

import com.template.spring.demo.application.exceptions.token.InvalidTokenException;

public interface TokenGateway {
    public TokenPayloadDTO decodeToken(String token) throws InvalidTokenException;
    public String generateToken(TokenPayloadDTO tokenPayload);
}
