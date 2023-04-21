package com.template.spring.demo.external.adapters.gateways.clients;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.template.spring.demo.core.application.interfaces.ports.hash.HashGateway;

import java.security.SecureRandom;

@Component
public class HashClientGateway implements HashGateway {
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());

    @Override
    public String hashValue(String value){
        String encodedValue = bCryptPasswordEncoder.encode(value);

        return encodedValue;
    }

    @Override
    public boolean compareHash(String value, String hash) {
        boolean isValueEqualsHash = bCryptPasswordEncoder.matches(value, hash);

        return isValueEqualsHash;
    }
}
