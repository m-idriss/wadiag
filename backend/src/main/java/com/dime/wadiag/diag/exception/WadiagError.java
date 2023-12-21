package com.dime.wadiag.diag.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WadiagError implements WadiagErrorResponse {

        TERM_NOT_FOUND("ID_NOT_FOUND", HttpStatus.NOT_FOUND, "Term with id [{id}] not found"),
        TERM_CONFLITS("ID_CONFLICT", HttpStatus.CONFLICT, "Term with id [{id}] make conflict"),
        WORD_NOT_FOUND("WORD_NOT_FOUND", HttpStatus.NOT_FOUND, "Word [{word}] do not exists"),
        FAILED_DEPENDENCY("WORD_NOT_FOUND", HttpStatus.FAILED_DEPENDENCY,
                        "Failed to connect to dependency code : [{code}] "),
        INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR,
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),

        ;

        String key;
        HttpStatus httpStatus;
        String message;

        public WadiagException exWithArguments(Map<String, Object> arguments) {
                return new WadiagException(this, arguments);
        }

}