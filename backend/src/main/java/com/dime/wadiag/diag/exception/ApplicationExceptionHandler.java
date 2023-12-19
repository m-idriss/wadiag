package com.dime.wadiag.diag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.NOT_FOUND, ex, request).responseEntity();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessage> handleException(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, ex, request).responseEntity();
    }

}
