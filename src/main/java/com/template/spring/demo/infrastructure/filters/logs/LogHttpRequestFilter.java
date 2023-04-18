package com.template.spring.demo.infrastructure.filters.logs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.template.spring.demo.application.interfaces.ports.log.LogGateway;
import com.template.spring.demo.infrastructure.interfaces.dtos.log_payload.LogPayloadDTO;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 1)  // Run right after the highest precedence
public class LogHttpRequestFilter implements Filter {

    @Autowired
    private LogGateway logger;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        long startTime = System.currentTimeMillis();

        chain.doFilter(requestWrapper, responseWrapper);

        long executionTime = System.currentTimeMillis() - startTime;
        String context = "entrypointAdapter";
        String operation = String.format("%s %s", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
        LogPayloadDTO.EnumLogOperationLevel logOperationLevel = this.getLogOperationLevel(httpServletResponse);
        Object params = this.getLogParams(requestWrapper);
        Object result = this.getLogResult(responseWrapper);
        Object details = this.getLogDetails(httpServletRequest);

        LogPayloadDTO logPayload = new LogPayloadDTO(
                logOperationLevel,
                context,
                operation,
                params,
                result,
                startTime,
                executionTime,
                details
        );
        boolean isErrorLog = logPayload.level == LogPayloadDTO.EnumLogOperationLevel.ERROR;
        if(isErrorLog){
            logger.error(logPayload);
        } else {
            logger.log(logPayload);
        }

        responseWrapper.copyBodyToResponse();
    }

    private LogPayloadDTO.EnumLogOperationLevel getLogOperationLevel(HttpServletResponse httpServletResponse){
        LogPayloadDTO.EnumLogOperationLevel logOperationLevel;
        boolean isSuccessfulResponse = httpServletResponse.getStatus() >= 200 && httpServletResponse.getStatus() <= 299;
        boolean isErrorResponse = httpServletResponse.getStatus() >= 400 && httpServletResponse.getStatus() <= 599;
        if(isSuccessfulResponse) return LogPayloadDTO.EnumLogOperationLevel.SUCCESS;
        if(isErrorResponse) return LogPayloadDTO.EnumLogOperationLevel.ERROR;
        return LogPayloadDTO.EnumLogOperationLevel.WARN;
    }

    private Object getLogParams(ContentCachingRequestWrapper requestWrapper) {
        Object requestBody;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            requestBody = objectMapper.readValue(requestWrapper.getContentAsByteArray(), Object.class);
        } catch(Exception exception) {
            requestBody = new HashMap<String, Object>();
        }

        Map params = new HashMap<String, Object>();
        Object requestQueryParams = requestWrapper.getParameterMap();
        Object requestHeaders = Collections.list(requestWrapper.getHeaderNames()).stream()
                .collect(Collectors.toMap(
                        (key) -> key,
                        (key) -> requestWrapper.getHeader(key),
                        (existingKey, newKey) -> newKey
                ));
        params.put("body", requestBody);
        params.put("queryParams", requestQueryParams);
        params.put("headers", requestHeaders);

        return params;
    }

    private Object getLogResult(ContentCachingResponseWrapper responseWrapper) {
        Object responseBody;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            responseBody = objectMapper.readValue(responseWrapper.getContentAsByteArray(), Object.class);
        } catch(Exception exception) {
            responseBody = new HashMap<String, Object>();
            ((HashMap<String, Object>) responseBody).put("status", responseWrapper.getStatus());
        }

        Map result = new HashMap<String, Object>();
        Object responseHeaders = responseWrapper.getHeaderNames().stream()
                .collect(Collectors.toMap(
                        (key) -> key,
                        (key) -> responseWrapper.getHeader(key),
                        (existingKey, newKey) -> newKey
                ));
        result.put("body", responseBody);
        result.put("headers", responseHeaders);

        return result;
    }

    private Object getLogDetails(HttpServletRequest httpServletRequest) {
        try {
            Map details = new HashMap<String, Object>();
            String ipAddress = httpServletRequest.getRemoteAddr();
            details.put("ipAddress", ipAddress);

            return details;
        } catch(Exception exception) {
            return new HashMap<String, Object>();
        }
    }

//

//    @Override
//    public void destroy() {
//        // do nothing
//    }
//
//    @Override
//    public void init(FilterConfig config) throws ServletException {
//        // do nothing
//    }

}