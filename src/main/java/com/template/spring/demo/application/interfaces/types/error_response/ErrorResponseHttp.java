package com.template.spring.demo.application.interfaces.types.error_response;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseHttp {
    public final int status;
    public final String error;
    public final List<String> messages;
}
