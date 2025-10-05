package com.microservices.order_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public class NotInStockException extends RuntimeException {
    private final String skuCode;
    private HttpStatusCode status;
    public NotInStockException(String message, String skuCode) {
        super(message);
        this.skuCode = skuCode;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public HttpStatusCode getStatus() {
        return status;
    }
}
