package com.example.microservice.order_service.config;

import com.example.microservice.order_service.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.Duration;

@Configuration
public class RestClientConfig {
    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Bean
    public InventoryClient inventoryClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .requestFactory(getClientHttpRequestFactory())
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServerProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServerProxyFactory.createClient(InventoryClient.class);
    }

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory ();
        factory.setConnectTimeout(Duration.ofSeconds(3));
        factory.setConnectTimeout(Duration.ofSeconds(3));
        return factory;
    }

}
