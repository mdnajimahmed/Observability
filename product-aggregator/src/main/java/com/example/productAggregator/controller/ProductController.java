package com.example.productAggregator.controller;

import com.example.productAggregator.dto.ProductDetailsResponseDto;
import com.example.productAggregator.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Mono<ProductDetailsResponseDto> getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }
}
