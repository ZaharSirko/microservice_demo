package com.example.microservice.order_service.DTO;


import java.io.Serializable;
import java.math.BigDecimal;


public record OrderRequest(String skuCode, BigDecimal price,
                           Integer quantity,UserDetails userDetails) implements Serializable {
    public record UserDetails(String email, String name) {}
}
