package com.template.spring.demo.adapters.gateways.clients;

import org.springframework.stereotype.Service;
import com.template.spring.demo.application.interfaces.ports.HashGateway;

import java.util.Objects;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import java.security.SecureRandom;

@Service
public class HashClientGateway implements HashGateway {
    @Override
    public String hashValue(String value){
        return new StringBuilder(value).reverse().toString();
//        int strength = 10; // work factor of bcrypt
//        BCryptPasswordEncoder bCryptPasswordEncoder =
//                new BCryptPasswordEncoder(strength, new SecureRandom());
//        String encodedPassword = bCryptPasswordEncoder.encode(password);
//
//        return encodedPassword;
    }

    @Override
    public boolean compareHash(String value, String hash) {
        String hashValue = this.hashValue(value);

        boolean isValueEqualsHash = Objects.equals(hashValue, hash);
        return isValueEqualsHash;
    }
}
