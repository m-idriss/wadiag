package com.dime.wadiag.diag.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenericExceptionTest {

    @DisplayName("Test getMessage() with message arguments")
    @Test
    void test_get_message_with_arguments() {
        GenericErrorResponse errorResponse = GenericError.TERM_NOT_FOUND;
        Map<String, Object> messageArguments = new HashMap<>();
        messageArguments.put("id", 45);

        GenericException genericException = new GenericException(errorResponse, messageArguments);

        String result = genericException.getMessage();

        assertEquals("Term with id [45] not found", result);
    }
}
