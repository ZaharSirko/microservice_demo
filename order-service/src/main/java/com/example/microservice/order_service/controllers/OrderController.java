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
    private  static final String PLACE_ORDER = "/orders";

    @PostMapping(PLACE_ORDER)
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest order) {
        orderService.createOrder(order);
        return new ResponseEntity<>("Order Placed Successfully", HttpStatus.CREATED);

    }
}
