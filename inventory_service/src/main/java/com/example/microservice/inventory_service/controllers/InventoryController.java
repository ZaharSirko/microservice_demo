package com.example.microservice.inventory_service.controllers;

import com.example.microservice.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    private static final String IS_IN_STOCK = "/inventory";

    @GetMapping(IS_IN_STOCK)
    public ResponseEntity<Boolean> isInStock (@RequestParam String skuCode, @RequestParam Integer quantity) {
        var inStock = inventoryService.isInStock(skuCode, quantity);
        return ResponseEntity.ok(inStock);

    }


}
