package com.dime.wadiag.diag.exception;

import org.springframework.http.HttpStatus;

public interface GenericErrorResponse {

    String getKey();

    String getMessage();

    HttpStatus getHttpStatus();
}