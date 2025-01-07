package com.example.microservice.order_service.controllers;

import com.example.microservice.order_service.DTO.OrderRequest;
import com.example.microservice.order_service.DTO.OrderResponse;
import com.example.microservice.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private static  final String FIND_ALL = "/orders";
    private  static final String ADD_ORDER = "/orders/add";

    @PostMapping(ADD_ORDER)
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest order) {
        var createdOrder = orderService.createOrder(order);
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);

    }
}
