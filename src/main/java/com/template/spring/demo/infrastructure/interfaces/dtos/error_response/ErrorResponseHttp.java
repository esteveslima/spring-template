package com.template.spring.demo.infrastructure.interfaces.dtos.error_response;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseHttp {
    public final int status;
    public final String error;
    public final List<String> messages;
}
