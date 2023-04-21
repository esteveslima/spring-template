package com.template.spring.demo.external.adapters.gateways.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.spring.demo.core.application.interfaces.ports.log.LogGateway;
import com.template.spring.demo.external.infrastructure.annotations.DisableAOP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//import java.util.Random;
//import java.util.UUID;

@Component
@DisableAOP // Necessary annotation used to mark the class allowing to easily filter it out from AOP, as this is a dependency for the logger AOP class and would cause an infinite loop
public class LogClientGateway implements LogGateway {

    private String loggerName = this.getClass().getName();
    private Logger logger = LoggerFactory.getLogger(loggerName);

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

//    private String generateUUID() {
//        Random random = new Random();
//        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
//        long variant3BitFlag = 0x8000000000000000L;
//        long least64SigBits = random63BitLong | variant3BitFlag;
//
//        final long currentTimeMillis = System.currentTimeMillis();
//        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
//        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
//        final long version = 1 << 12;
//        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
//        long most64SigBits =  time_low | time_mid | version | time_hi;
//
//        UUID timeBasedUUID = new UUID(most64SigBits, least64SigBits);
//
//        return timeBasedUUID.toString();
//    }
}
