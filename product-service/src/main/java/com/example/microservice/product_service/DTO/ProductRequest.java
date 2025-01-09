package com.example.microservice.product_service.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProductRequest(String name, String skuCode, String description, BigDecimal price) implements Serializable {
}
