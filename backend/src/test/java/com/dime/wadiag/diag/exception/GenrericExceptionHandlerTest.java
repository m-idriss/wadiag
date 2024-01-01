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

import com.dime.wadiag.diag.model.GenericConstants;

class GenrericExceptionHandlerTest {
    @DisplayName("Handle WadiagException for Resource Not Found")
    @Test
    void test_handle_resource_not_found_exception() {
        // Arrange
        GenericException ex = new GenericException(GenericError.WORD_NOT_FOUND, Map.of("word", "toto"));
        WebRequest request = mock(WebRequest.class);
        when(request.getContextPath()).thenReturn("/api");

        // Act
        ResponseEntity<Map<String, Object>> response = new GenericExceptionHandler()
                .handle(ex, request);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.NOT_FOUND.value(), body.get(GenericConstants.STATUS));
        assertEquals(HttpStatus.NOT_FOUND, body.get(GenericConstants.ERROR));
        assertEquals(GenericError.WORD_NOT_FOUND.toString(), body.get(GenericConstants.ERROR_KEY));
        assertEquals(String.format("Word [%s] do not exists", "toto"), body.get(GenericConstants.MESSAGE));
        assertEquals("/api", body.get(GenericConstants.PATH));

    }

}
