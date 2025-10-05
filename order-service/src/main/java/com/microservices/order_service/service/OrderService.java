package com.microservices.order_service.service;

import com.microservices.order_service.client.InventoryClient;
import com.microservices.order_service.dto.OrderRequest;
import com.microservices.order_service.exception.NotInStockException;
import com.microservices.order_service.model.Order;
import com.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public Order placeOrder(OrderRequest orderRequest) throws NotInStockException {
        var isInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if (!isInStock) {
            throw new NotInStockException("Product with SKU code " + orderRequest.skuCode() + " is not in stock.", orderRequest.skuCode());
        }
        Order order = mapToOrder(orderRequest);
        order = orderRepository.save(order);
        log.info("Order {} with order number {} has been placed", order.getId(), order.getOrderNumber());
        return order;
    }

    private Order mapToOrder(OrderRequest orderRequest) {
        return Order.builder()
                .orderNumber(orderRequest.orderNumber())
                .skuCode(orderRequest.skuCode())
                .price(orderRequest.price())
                .quantity(orderRequest.quantity())
                .build();
    }
}
