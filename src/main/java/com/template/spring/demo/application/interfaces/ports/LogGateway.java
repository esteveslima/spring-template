package com.template.spring.demo.application.interfaces.ports;

public interface LogGateway {
    public void log(Object payloadMessage);
    public void error(Object payloadMessage);
}
