package com.example.microservice.order_service.service;

import com.example.microservice.order_service.DTO.OrderRequest;
import com.example.microservice.order_service.DTO.OrderResponse;
import com.example.microservice.order_service.model.Order;
import com.example.microservice.order_service.repo.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderResponse(order.getId(),order.getOrderNumber(),order.getSkuCode(),order.getPrice(),
                        order.getQuantity()))
                .toList();
    };
    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        try{
            Order order = Order.builder()
                    .skuCode(orderRequest.skuCode())
                    .price(orderRequest.price())
                    .quantity(orderRequest.quantity())
                    .build();
            orderRepository.save(order);
            log.info("Order created: {}", order);
            return new OrderResponse(order.getId(),order.getOrderNumber(),order.getSkuCode(),order.getPrice(),order.getQuantity());
        }
        catch(Exception e){
            throw new RuntimeException("Error saving order: " + e.getMessage(), e);
        }
    }
}
