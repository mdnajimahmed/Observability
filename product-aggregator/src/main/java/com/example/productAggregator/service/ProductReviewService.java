package com.example.productAggregator.service;

import com.example.productAggregator.client.ProductReviewClient;
import com.example.productAggregator.dto.ProductReviewDto;
import com.example.productReview.grpc.reviews.ReviewResponse;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@Service
public class ProductReviewService {
    private final ProductReviewClient productReviewClient;

    public ProductReviewService(ProductReviewClient productReviewClient) {
        this.productReviewClient = productReviewClient;
    }
    public Mono<List<ProductReviewDto>> getReviewsByProductId(Long productId) {
        return productReviewClient.getReviewsByProductId(productId)
                .map(this::convertToProductReview)
                .collectList()
                .onErrorResume(e -> Mono.just(Collections.emptyList()));
    }

    private ProductReviewDto convertToProductReview(ReviewResponse reviewResponse) {
        return ProductReviewDto.builder()
                .id(reviewResponse.getReviewId())
                .productId(reviewResponse.getProductId())
                .userId(reviewResponse.getUserId())
                .rating(reviewResponse.getRating())
                .comment(reviewResponse.getComment())
                .createdAt(reviewResponse.getCreatedAt())
                .updatedAt(reviewResponse.getUpdatedAt())
                .build();
    }
}
