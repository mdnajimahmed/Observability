package com.example.productAggregator.controller;

import com.example.productAggregator.dto.ProductDetailsDto;
import com.example.productAggregator.dto.ProductInfoResponseDto;
import com.example.productAggregator.service.ProductAggregatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    private final ProductAggregatorService productAggregatorService;

    public ProductController(ProductAggregatorService productAggregatorService) {
        this.productAggregatorService = productAggregatorService;
    }

    @GetMapping("/{id}")
    public Mono<ProductInfoResponseDto> getProduct(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        log.info("Secure endpoint accessed by: {}", email);
        return productAggregatorService.getProduct(id);
    }


    @GetMapping("/public/test")
    public Mono<String> publicEndpoint() {
        return Mono.just("Public Endpoint");
    }
}
