package com.template.spring.demo.core.application.interfaces.ports.log;

public interface LogGateway {
    public void log(Object payloadMessage);
    public void error(Object payloadMessage);
}
