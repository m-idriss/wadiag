package com.dime.wadiag.diag.exception;

import org.springframework.http.HttpStatus;

public interface WadiagErrorResponse {

    String getKey();

    String getMessage();

    HttpStatus getHttpStatus();
}