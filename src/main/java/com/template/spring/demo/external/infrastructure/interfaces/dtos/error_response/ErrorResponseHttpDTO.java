package com.template.spring.demo.external.infrastructure.interfaces.dtos.error_response;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponseHttpDTO {
    public final int status;
    public final String error;
    public final List<String> messages;
}
