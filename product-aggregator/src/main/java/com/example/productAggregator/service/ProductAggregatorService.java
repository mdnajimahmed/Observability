package com.example.productAggregator.service;

import com.example.productAggregator.dto.ProductDetailsDto;
import com.example.productAggregator.dto.ProductInfoResponseDto;
import com.example.productAggregator.dto.ProductReviewDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ProductAggregatorService {
    private final ProductDetailsService productDetailsService;
    private final ProductReviewService productReviewService;

    public ProductAggregatorService(ProductDetailsService productDetailsService, ProductReviewService productReviewService) {
        this.productDetailsService = productDetailsService;
        this.productReviewService = productReviewService;
    }


    public Mono<ProductInfoResponseDto> getProduct(Long id) {
        return Mono.zip(productDetailsService.getProduct(id), productReviewService.getReviewsByProductId(id))
                .flatMap(tuple -> {
                    ProductDetailsDto productDetailsDto = tuple.getT1();
                    List<ProductReviewDto> reviewsDto = tuple.getT2();

                    // Calculate average rating
                    Double averageRating = calculateAverageRating(reviewsDto);

                    // Build response DTO
                    return Mono.just(ProductInfoResponseDto.create(id,productDetailsDto,reviewsDto));
                }).switchIfEmpty(Mono.error(new RuntimeException("Product not found with id: " + id)));

    }

    private Double calculateAverageRating(List<ProductReviewDto> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return null;
        }
        return reviews.stream()
                .mapToInt(ProductReviewDto::getRating)
                .average()
                .orElse(0.0);
    }

}
