package com.template.spring.demo.application.ports;

public interface HashGateway {
    public String hashValue(String value);
    public boolean compareHash(String value, String hash);
}
