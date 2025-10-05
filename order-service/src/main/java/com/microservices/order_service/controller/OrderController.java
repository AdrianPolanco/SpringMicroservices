package com.microservices.order_service.controller;

import com.microservices.order_service.dto.OrderRequest;
import com.microservices.order_service.error.ErrorResponse;
import com.microservices.order_service.exception.NotInStockException;
import com.microservices.order_service.model.Order;
import com.microservices.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrder(@RequestBody OrderRequest orderRequest) {

        return orderService.placeOrder(orderRequest);
    }

    @ExceptionHandler(NotInStockException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleNotInStockException(NotInStockException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(),
                HttpStatus.BAD_REQUEST, LocalDateTime.now(),
                "SKU Code: " + ex.getSkuCode());

        log.error("NotInStockException: {}", errorResponse.getMessage());
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
