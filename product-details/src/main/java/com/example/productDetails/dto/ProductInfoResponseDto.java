package com.example.productDetails.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class ProductInfoResponseDto {
    private Long id;

    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Boolean isActive;

    private List<String> tags;

    private Map<String, String> metadata;

    private String imageUrl;

    private String location;

    private BigDecimal weight;

    private String dimensions;
}
