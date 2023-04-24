package com.template.spring.demo.external.infrastructure.aspects.exceptions;

import com.template.spring.demo.core.application.interfaces.ports.log.LogGateway;
import com.template.spring.demo.external.infrastructure.interfaces.dtos.error_response.ErrorResponseHttpDTO;
import com.template.spring.demo.external.infrastructure.interfaces.dtos.log_payload.LogPayloadDTO;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionRestControllerAdvice {

    @Autowired private LogGateway logger;

    @ExceptionHandler(value = ResponseStatusException.class)
    private ResponseEntity<ErrorResponseHttpDTO> handleHttpException(ResponseStatusException exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(exception.getReason());

        ErrorResponseHttpDTO errorResponseHttp = new ErrorResponseHttpDTO(
                exception.getStatusCode().value(),
                exception.getStatusCode().toString(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    //

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<ErrorResponseHttpDTO> handleBadRequestBodyException(MethodArgumentNotValidException exception) {
        List<String> errorMessages = exception.getBindingResult().getFieldErrors().stream()
                .map((error) -> String.format("Field '%s' error: %s", error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorResponseHttpDTO errorResponseHttp = new ErrorResponseHttpDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)   // exclusively added for path/query params validation in controllers
    private ResponseEntity<ErrorResponseHttpDTO> handleBadRequestParamsValidationException(ConstraintViolationException exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(exception.getMessage());

        ErrorResponseHttpDTO errorResponseHttp = new ErrorResponseHttpDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    private ResponseEntity<ErrorResponseHttpDTO> handleBadRequestParamsException(MethodArgumentTypeMismatchException exception) {
        List<String> errorMessages = new ArrayList<String>();
        String exceptionUsefulMessage = String.join(",", Arrays.stream(exception.getMessage().split(";")).skip(1).toList());
        errorMessages.add(exceptionUsefulMessage);

        ErrorResponseHttpDTO errorResponseHttp = new ErrorResponseHttpDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    private ResponseEntity<ErrorResponseHttpDTO> handleBadRequestContentException(HttpMessageNotReadableException exception) {
        List<String> errorMessages = new ArrayList<String>();
        String exceptionUsefulMessage = String.join(",", Arrays.stream(exception.getMessage().split(":")).skip(2).toList());
        errorMessages.add(exceptionUsefulMessage);

        ErrorResponseHttpDTO errorResponseHttp = new ErrorResponseHttpDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<ErrorResponseHttpDTO> handleBadRequestMethodException(HttpRequestMethodNotSupportedException exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(exception.getMessage());

        ErrorResponseHttpDTO errorResponseHttp = new ErrorResponseHttpDTO(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    //

    // TODO: not differentiating authentication and authorization, everything resolves to the same exception
    @ExceptionHandler(value = AccessDeniedException.class)
    private ResponseEntity<ErrorResponseHttpDTO> handleAuthException(AccessDeniedException exception) {
        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(HttpStatus.UNAUTHORIZED.getReasonPhrase());

        ErrorResponseHttpDTO errorResponseHttp = new ErrorResponseHttpDTO(
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }

    //

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponseHttpDTO> handleAnyException(Exception exception) {
        this.logger.error(exception.getMessage()); // Probably a good idea to log the unknown exception

        List<String> errorMessages = new ArrayList<String>();
        errorMessages.add(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

        ErrorResponseHttpDTO errorResponseHttp = new ErrorResponseHttpDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                errorMessages
        );

        return ResponseEntity.status(errorResponseHttp.status).body(errorResponseHttp);
    }


}
