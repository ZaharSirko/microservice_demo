package com.example.microservice.order_service.service;

import com.example.microservice.order_service.DTO.OrderRequest;
import com.example.microservice.order_service.DTO.OrderResponse;
import com.example.microservice.order_service.client.InventoryClient;
import com.example.microservice.order_service.event.OrderPlacedEvent;
import com.example.microservice.order_service.model.Order;
import com.example.microservice.order_service.repo.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;



    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> new OrderResponse(order.getId(),order.getOrderNumber(),order.getSkuCode(),order.getPrice(),
                        order.getQuantity()))
                .toList();
    };

    @Transactional
    public void createOrder(OrderRequest orderRequest) {
      var inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
       if(inStock){
           try{
               Order order = Order.builder()
                       .skuCode(orderRequest.skuCode())
                       .price(orderRequest.price().multiply(BigDecimal.valueOf(orderRequest.quantity())))
                       .quantity(orderRequest.quantity())
                       .build();
               orderRepository.save(order);
               log.info("Order created: {}", order);

//               var orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),
//                       orderRequest
//                       .userDetails().email(),
//                       orderRequest.userDetails().firstName(),
//                       orderRequest.userDetails().lastName());
//
//               log.info("Start- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);
//               kafkaTemplate.send("order-placed", orderPlacedEvent);
//               log.info("End- Sending OrderPlacedEvent {} to Kafka Topic", orderPlacedEvent);

           }
           catch(Exception e){
               throw new RuntimeException("Error saving order: " + e.getMessage(), e);
           }
       }
       else {
           throw new RuntimeException("Product not in stock with SkuCode: " + orderRequest.skuCode());
       }

    }
}
