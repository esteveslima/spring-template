package com.template.spring.demo.core.application.interfaces.ports.hash;

public interface HashGateway {
    public String hashValue(String value);
    public boolean compareHash(String value, String hash);
}
