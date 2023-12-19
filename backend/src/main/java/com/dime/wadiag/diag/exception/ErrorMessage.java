package com.dime.wadiag.diag.exception;

import java.net.URI;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorMessage {
    private URI type;
    private String title;
    private HttpStatus status;
    private ZonedDateTime timestamp;
    private String detail;

    public int getStatus() {
        return status.value();
    }

    public ErrorMessage(HttpStatus httpStatus, Exception ex, WebRequest request) {
        this(URI.create(request.getContextPath()),
                httpStatus.getReasonPhrase(),
                httpStatus,
                ZonedDateTime.now(),
                ex.getMessage());
    }

    public ResponseEntity<ErrorMessage> responseEntity() {
        return ResponseEntity.status(status).body(this);
    }

}
