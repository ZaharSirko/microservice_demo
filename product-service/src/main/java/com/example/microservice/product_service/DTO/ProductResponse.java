package com.example.microservice.product_service.DTO;

import java.io.Serializable;
import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price) implements Serializable {
}
