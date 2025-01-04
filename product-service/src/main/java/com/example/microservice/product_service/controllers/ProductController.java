package com.example.microservice.product_service.controllers;

import com.example.microservice.product_service.DTO.ProductRequest;
import com.example.microservice.product_service.DTO.ProductResponse;
import com.example.microservice.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private static  final String FIND_ALL = "/products";
    private  static final String ADD_PRODUCT = "/products/add";
    private  static final String UPDATE_PRODUCT = "/products/update";
    private  static final String DELETE_PRODUCT = "/products/delete";


    @GetMapping(FIND_ALL)
    public ResponseEntity<List<ProductResponse>> findAll() {
        var products = productService.findAll();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping(ADD_PRODUCT)
   public ResponseEntity<ProductResponse> addNewProduct(@RequestBody ProductRequest productRequest) {
     var createdProduct = productService.createProduct(productRequest);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }
}
