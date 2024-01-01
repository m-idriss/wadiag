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

import com.dime.wadiag.diag.model.GenericConstants;

import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
/**
 * https://medium.com/@iyusubov444/spring-rest-api-exception-handling-a312eafe85e7
 */
public class GenericExceptionHandler extends DefaultErrorAttributes {

    @ExceptionHandler(GenericException.class)
    public ResponseEntity<Map<String, Object>> handle(GenericException ex,
            WebRequest request) {
        return ofType(request, ex.getErrorResponse().getHttpStatus(), ex.getErrorResponse().getKey(), ex.getMessage());
    }

    private ResponseEntity<Map<String, Object>> ofType(WebRequest request, HttpStatus status,
            String key, String message) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(GenericConstants.STATUS, status.value());
        attributes.put(GenericConstants.ERROR, status);
        attributes.put(GenericConstants.ERROR_KEY, key);
        attributes.put(GenericConstants.PATH, getRequestPath(request));
        attributes.put(GenericConstants.MESSAGE, message);
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
