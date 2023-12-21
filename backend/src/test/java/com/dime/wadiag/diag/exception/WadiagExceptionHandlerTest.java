package com.dime.wadiag.diag.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

class WadiagExceptionHandlerTest {
    @DisplayName("Handle WadiagException for Resource Not Found")
    @Test
    void test_handle_resource_not_found_exception() {
        // Arrange
        WadiagException ex = new WadiagException(WadiagError.WORD_NOT_FOUND, Map.of("word", "toto"));
        WebRequest request = mock(WebRequest.class);
        when(request.getContextPath()).thenReturn("/api");

        // Act
        ResponseEntity<Map<String, Object>> response = new WadiagExceptionHandler()
                .handle(ex, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.NOT_FOUND.value(), body.get(WadiagHttpResponseConstants.STATUS));
        assertEquals(HttpStatus.NOT_FOUND, body.get(WadiagHttpResponseConstants.ERROR));
        assertEquals(WadiagError.WORD_NOT_FOUND.toString(), body.get(WadiagHttpResponseConstants.ERROR_KEY));
        assertEquals("/api", body.get(WadiagHttpResponseConstants.PATH));

    }

}
