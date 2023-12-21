package com.dime.wadiag.diag.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WadiagHttpResponseConstants {

    public static final String STATUS = "status";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String ERROR_KEY = "errorKey";
    public static final String PATH = "path";

}