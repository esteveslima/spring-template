package com.template.spring.demo.application.interfaces.types.log_payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogPayload {
    public EnumLogOperationLevel level;
    public String context;
    public String operation;
    public Object params;
    public Object result;
    public long startTime;
    public long executionTime;
    public Object details;
}