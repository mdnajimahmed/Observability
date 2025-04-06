package com.example.productAggregator.client;

import com.example.productAggregator.dto.ProductDetailsDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class ProductDetailsClient {
    private final WebClient client;

    public ProductDetailsClient(WebClient.Builder builder,
                                @Value("${app.egress.product-details-host}") String productDetailsHost) {
        log.info("productDetailsHost = {}", productDetailsHost);
        this.client = builder.baseUrl(productDetailsHost).build();
    }

    @CircuitBreaker(name = "productDetailsClient", fallbackMethod = "fallbackProductDetails")
    public Mono<ProductDetailsDto> getProduct(Long productId) {
        return this.client
                .get()
                .uri("/product-details/{productId}", productId)
                .retrieve()
                .bodyToMono(ProductDetailsDto.class);
    }

    public Mono<ProductDetailsDto> fallbackProductDetails(Long productId, Throwable throwable) {
        log.error("fallbackProductDetails = {} " , productId, throwable);
        return Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

}
