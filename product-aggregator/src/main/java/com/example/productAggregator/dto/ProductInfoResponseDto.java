package com.example.productAggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

//@NoArgsConstructor
//@AllArgsConstructor
@Data
@AllArgsConstructor(staticName = "create")
public class ProductInfoResponseDto {
    private Long id;
    private ProductDetailsDto productDetails;
    private List<ProductReviewDto> productReviewDtos;

}
