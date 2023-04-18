package com.template.spring.demo.infrastructure.aspects.logs;

import com.template.spring.demo.application.interfaces.ports.log.LogGateway;
import com.template.spring.demo.infrastructure.interfaces.dtos.log_payload.LogPayloadDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Aspect
@Component
public class LogAspect {
    private String a = "";
    @Autowired
    private LogGateway logger;

    //

    @Pointcut("""
            !@annotation(com.template.spring.demo.infrastructure.annotations.DisableAOP) &&
            !@target(com.template.spring.demo.infrastructure.annotations.DisableAOP)
            """)
    public void enabledAOP() {}

    @Pointcut("execution(* com.template.spring.demo.adapters.gateways..*.*(*))")
    public void gatewayAdaptersMethods() {}

    @Pointcut("gatewayAdaptersMethods() && enabledAOP()")
    public void validGatewayAdaptersMethods() {}

    //

    @Around(value = "validGatewayAdaptersMethods() ")
    private Object logGatewayAdapterAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String context = "gatewayAdapter";
        String operation = joinPoint.getSignature().toString();
        Object params = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();

        LogPayloadDTO logPayload = new LogPayloadDTO();
        logPayload.setContext(context);
        logPayload.setOperation(operation);
        logPayload.setParams(params);
        logPayload.setStartTime(startTime);

        try{
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            Map details = new HashMap<String, Object>();

            logPayload.setLevel(LogPayloadDTO.EnumLogOperationLevel.SUCCESS);
            logPayload.setResult(result);
            logPayload.setExecutionTime(executionTime);
            logPayload.setDetails(details);

            logger.log(logPayload);

            return result;
        } catch(Exception exception) {
            Object result = exception.getClass().getName();
            long executionTime = System.currentTimeMillis() - startTime;
            Map details = new HashMap<String, Object>();
            Optional<Throwable> optionalCause = Optional.ofNullable(exception.getCause());
            if(optionalCause.isPresent()) {
                details.put("exceptionCause", optionalCause.get().toString());
            }
            Optional<Object> optionalPayload = this.getPropertyContent(exception, "payload");
            if(optionalPayload.isPresent()) {
                details.put("exceptionPayload", optionalPayload.get());
            }

            logPayload.setLevel(LogPayloadDTO.EnumLogOperationLevel.ERROR);
            logPayload.setResult(result);
            logPayload.setExecutionTime(executionTime);
            logPayload.setDetails(details);

            logger.error(logPayload);

            throw exception;
        }
    }

    private Optional<Object> getPropertyContent(Object myObject, String propertyName) {
        try {
            Field field = myObject.getClass().getDeclaredField(propertyName);
            field.setAccessible(true);
            Object propertyContent = field.get(myObject);
            return Optional.ofNullable(propertyContent);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return Optional.empty();
        }
    }
}