package com.example.microservice.product_service.service;

import com.example.microservice.product_service.DTO.ProductRequest;
import com.example.microservice.product_service.DTO.ProductResponse;
import com.example.microservice.product_service.model.Product;
import com.example.microservice.product_service.repo.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
private final ProductRepository productRepository;


    public List<ProductResponse> findAll() {
        return productRepository.findAll()
                .stream()
                .map(product -> new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice()))
                .toList();
    }

    //    public Product findById(int id) {}

    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {
        try {
            Product product = Product.builder()
                    .name(productRequest.name())
                    .description(productRequest.description())
                    .price(productRequest.price())
                    .build();
            productRepository.save(product);
            log.info("Product created: {}", product);
            return new ProductResponse(product.getId(),product.getName(),product.getDescription(),product.getPrice());
        } catch (Exception e) {

            throw new RuntimeException("Error saving product: " + e.getMessage(), e);
        }

    }

//    public void delete(int id) {}

}
