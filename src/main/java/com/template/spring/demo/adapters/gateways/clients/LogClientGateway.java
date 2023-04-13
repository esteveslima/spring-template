package com.template.spring.demo.adapters.gateways.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.spring.demo.application.interfaces.ports.LogGateway;
import com.template.spring.demo.infrastructure.annotations.DisableAOP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@DisableAOP // Necessary annotation used to mark the class allowing to easily filter it out from AOP, as this is a dependency for the logger AOP class and would cause an infinite loop
@RequestScope // Necessary to assign a unique ID, allowing to group different logs in the same request scope
@Service
public class LogClientGateway implements LogGateway {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Override
    public void log(Object payloadMessage){
        String stringifiedPayloadMessage = this.stringifyObject(payloadMessage);

        logger.info(stringifiedPayloadMessage);
    }

    @Override
    public void error(Object payloadMessage){
        String stringifiedPayloadMessage = this.stringifyObject(payloadMessage);

        logger.error(stringifiedPayloadMessage);
    }


    //

    private String stringifyObject(Object obj) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(obj);
            return jsonString;
        } catch(JsonProcessingException exception) {
            System.err.println("Error converting JSON to object: " + exception.getMessage());
            return obj.toString();
        }
    }

}
