package com.example.productAggregator.controller;

import com.example.productAggregator.dto.ProductDetailsResponseDto;
import com.example.productAggregator.service.ProductService;
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
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Mono<ProductDetailsResponseDto> getProduct(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");
        log.info("Secure endpoint accessed by: {}", email);
        return productService.getProduct(id);
    }


    @GetMapping("/public/test")
    public Mono<String> publicEndpoint() {
        return Mono.just("Public Endpoint");
    }
}
