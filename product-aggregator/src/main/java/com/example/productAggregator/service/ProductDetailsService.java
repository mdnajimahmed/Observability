package com.example.productAggregator.service;

import com.example.productAggregator.client.ProductDetailsClient;
import com.example.productAggregator.dto.ProductDetailsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductDetailsService {
    private final ProductDetailsClient productDetailsClient;

    public ProductDetailsService(ProductDetailsClient productDetailsClient) {
        this.productDetailsClient = productDetailsClient;
    }

    public Mono<ProductDetailsDto> getProduct(Long id) {
        log.info("Get product by id: {}", id);
        return productDetailsClient.getProduct(id)
                .doOnNext(resp -> log.info("Product details received: {}", resp));
    }
}
