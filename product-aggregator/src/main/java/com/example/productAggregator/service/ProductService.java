package com.example.productAggregator.service;

import com.example.productAggregator.dto.ProductDetailsResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductService {
    public Mono<ProductDetailsResponseDto> getProduct(Long id) {
        log.info("Get product by id: {}", id);
        return Mono.just(ProductDetailsResponseDto.create(id));
    }
}
