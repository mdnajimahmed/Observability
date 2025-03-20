package com.example.productDetails.service;

import com.example.productDetails.dto.ProductInfoResponseDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductInfoService {

    public Mono<ProductInfoResponseDto> getProductInfo(Long id) {
        return Mono.just(ProductInfoResponseDto.create(id));
    }
}
