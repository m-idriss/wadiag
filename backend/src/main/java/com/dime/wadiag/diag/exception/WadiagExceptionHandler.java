package com.dime.wadiag.diag.exception;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
/**
 * https://medium.com/@iyusubov444/spring-rest-api-exception-handling-a312eafe85e7
 */
public class WadiagExceptionHandler extends DefaultErrorAttributes {

    @ExceptionHandler(WadiagException.class)
    public ResponseEntity<Map<String, Object>> handle(WadiagException ex,
            WebRequest request) {
        return ofType(request, ex.getErrorResponse().getHttpStatus(), ex.getErrorResponse().getKey());
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status,
            String key) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(WadiagHttpResponseConstants.STATUS, status.value());
        attributes.put(WadiagHttpResponseConstants.ERROR, status);
        attributes.put(WadiagHttpResponseConstants.ERROR_KEY, key);
        attributes.put(WadiagHttpResponseConstants.PATH, getRequestPath(request));
        return new ResponseEntity<>(attributes, status);
    }

    private String getRequestPath(WebRequest request) {
        if (request instanceof ServletWebRequest) {
            return ((ServletWebRequest) request).getRequest().getRequestURI();
        } else {
            return request.getContextPath();
        }
    }
}
