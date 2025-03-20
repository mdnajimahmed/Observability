package com.example.productDetails.controller;

import com.example.productDetails.dto.ProductInfoResponseDto;
import com.example.productDetails.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product-details")
@Slf4j
public class ProductDetailsController {
    private final ProductInfoService productInfoService;

    public ProductDetailsController(ProductInfoService productInfoService) {
        this.productInfoService = productInfoService;
    }

    @GetMapping("/{id}")
    public Mono<ProductInfoResponseDto> getProduct(@PathVariable Long id) {
        log.info("Returning product info for : {}", id);
        return productInfoService.getProductInfo(id);
    }
}
