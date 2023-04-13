package com.template.spring.demo.infrastructure.aspects.exceptions;

import com.template.spring.demo.infrastructure.interfaces.dtos.error_response.ErrorResponseHttp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponseHttp> handleAnyException(Exception exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        ErrorResponseHttp errorResponseHttp = new ErrorResponseHttp(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponseHttp> handleBadRequestHttpException(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map(
                        error -> String.format("Field '%s' error: %s", error.getField(), error.getDefaultMessage())
                )
                .collect(Collectors.toList());

        ErrorResponseHttp errorResponseHttp = new ErrorResponseHttp(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    private ResponseEntity<ErrorResponseHttp> handleHttpException(ResponseStatusException exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(exception.getReason());

        ErrorResponseHttp errorResponseHttp = new ErrorResponseHttp(
                exception.getStatusCode().value(),
                exception.getStatusCode().toString(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

}
