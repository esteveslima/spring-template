package com.template.spring.demo.external.adapters.gateways.clients;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.spring.demo.core.application.exceptions.token.InvalidTokenException;
import com.template.spring.demo.core.application.interfaces.auth_token.AuthTokenPayloadDTO;
import com.template.spring.demo.core.application.interfaces.ports.token.TokenGateway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Component
public class TokenClientGateway implements TokenGateway<AuthTokenPayloadDTO>{


    @Value("${JWT_SECRET}") private String jwtSecret;
    @Value("${JWT_EXPIRES_SECONDS}") private String jwtExpiresSeconds;

    @Override
    public AuthTokenPayloadDTO decodeToken(String token) throws InvalidTokenException {
        try {
            DecodedJWT decodedJWT = JWT
                    .require(Algorithm.HMAC256(this.jwtSecret))
                    .build()
                    .verify(token);

            String tokenStringPayloadPart = decodedJWT.getPayload();
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String stringifiedPayload = new String(decoder.decode(tokenStringPayloadPart));

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            Map<String, Object> payloadMap = objectMapper.readValue(stringifiedPayload, Map.class);
            AuthTokenPayloadDTO tokenPayload = objectMapper.convertValue(payloadMap, AuthTokenPayloadDTO.class);

            return tokenPayload;
        } catch(JWTVerificationException exception) {
            throw new InvalidTokenException(token, exception);
        } catch(Exception exception) {
            throw new InvalidTokenException(token, exception);
        }
    }

    @Override
    public String generateToken(AuthTokenPayloadDTO tokenPayload) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.SECOND, Integer.parseInt(this.jwtExpiresSeconds));
        Date jwtExpireDate = cal.getTime();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> mappedPayload = objectMapper.convertValue(tokenPayload, Map.class);

        String token = JWT.create()
                .withPayload(mappedPayload)
                .withExpiresAt(jwtExpireDate)
                .sign(Algorithm.HMAC256(this.jwtSecret));

        return token;
    }
}
