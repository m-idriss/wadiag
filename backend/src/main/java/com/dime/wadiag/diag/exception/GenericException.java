package com.dime.wadiag.diag.exception;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GenericException extends RuntimeException {

    private static final long serialVersionUID = -3784903329806863768L;
    private final transient GenericErrorResponse errorResponse;
    private final transient Map<String, Object> messageArguments;

    @Override
    public String getMessage() {
        return messageArguments.isEmpty() ? errorResponse.getMessage()
                : StringSubstitutor.replace(errorResponse.getMessage(), messageArguments, "{", "}");
    }

}