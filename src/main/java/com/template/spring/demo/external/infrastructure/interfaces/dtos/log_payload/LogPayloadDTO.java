package com.template.spring.demo.external.infrastructure.interfaces.dtos.log_payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogPayloadDTO {
    public EnumLogOperationLevel level;
    public String context;
    public String operation;
    public Object params;
    public Object result;
    public long startTime;
    public long executionTime;
    public Object details;

    public static enum EnumLogOperationLevel {
        SUCCESS,
        WARN,
        ERROR
    };
}