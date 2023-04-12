package com.template.spring.demo.infrastructure.aspects.logs;

import com.template.spring.demo.application.interfaces.ports.LogGateway;
import com.template.spring.demo.application.interfaces.types.log_payload.EnumLogOperationLevel;
import com.template.spring.demo.application.interfaces.types.log_payload.LogPayload;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        LogPayload logPayload = new LogPayload();
        logPayload.setContext(context);
        logPayload.setOperation(operation);
        logPayload.setParams(params);
        logPayload.setStartTime(startTime);

        Object result;
        try{
            result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;

            logPayload.setLevel(EnumLogOperationLevel.SUCCESS);
            logPayload.setResult(result);
            logPayload.setExecutionTime(executionTime);

            logger.log(logPayload);

            return result;
        } catch(Exception exception) {
            result = exception.toString();
            long executionTime = System.currentTimeMillis() - startTime;

            logPayload.setLevel(EnumLogOperationLevel.ERROR);
            logPayload.setResult(result);
            logPayload.setExecutionTime(executionTime);

            logger.error(logPayload);

            throw exception;
        }
    }
}