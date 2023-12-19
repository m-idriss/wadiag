package com.dime.wadiag.diag.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

class ApplicationExceptionHandlerTest {
    @DisplayName("Should handle ResourceNotFoundException and return a ResponseEntity with status code 404 and an ErrorMessage object with the correct values")
    @Test
    void test_handle_resource_not_found_exception() {
        // Arrange
        ApplicationExceptionHandler controllerExceptionHandler = new ApplicationExceptionHandler();
        ResourceNotFoundException ex = new ResourceNotFoundException("Resource not found");
        WebRequest request = mock(WebRequest.class);
        when(request.getContextPath()).thenReturn("/api");

        // Act
        ResponseEntity<ErrorMessage> response = controllerExceptionHandler.handleNotFoundException(ex, request);

        // Assert
        assertNotNull(response, "Response should not be null");
        ErrorMessage body = response.getBody();
        assertNotNull(body, "Response body should not be null");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), body.getTitle());
        assertEquals(HttpStatus.NOT_FOUND.value(), body.getStatus());
        assertEquals("Resource not found", body.getDetail());
    }

}
