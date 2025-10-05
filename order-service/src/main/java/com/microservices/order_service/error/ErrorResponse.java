package com.microservices.order_service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

public class ErrorResponse {
    private final String message;
    private final HttpStatusCode status;
    private final LocalDateTime timestamp;
    private final String details;

    public ErrorResponse(String message, HttpStatusCode status, LocalDateTime timestamp, String details) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDetails() {
        return details;
    }
}
