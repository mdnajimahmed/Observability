package com.example.productDetails.service;

import com.example.productDetails.dto.ProductInfoResponseDto;
import com.example.productDetails.repository.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProductInfoService {
    private final ProductRepository productRepository;

    public ProductInfoService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Mono<ProductInfoResponseDto> getProductInfo(Long id) {
        return productRepository.findById(id).map(
                product -> {
                    // Create a new ProductDTO
                    ProductInfoResponseDto productDTO = new ProductInfoResponseDto();

                    BeanUtils.copyProperties(product,productDTO);

                    return productDTO;
                }
        );

    }
}
