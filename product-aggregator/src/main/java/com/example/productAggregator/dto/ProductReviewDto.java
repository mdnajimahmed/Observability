package com.example.productAggregator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReviewDto {
    private Long id;
    private Long productId;
    private Long userId;
    private Integer rating;
    private String comment;
    private String createdAt;
    private String updatedAt;
}