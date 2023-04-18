package com.template.spring.demo.application.interfaces.ports.hash;

public interface HashGateway {
    public String hashValue(String value);
    public boolean compareHash(String value, String hash);
}
