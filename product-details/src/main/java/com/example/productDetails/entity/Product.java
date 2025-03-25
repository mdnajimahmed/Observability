package com.example.productDetails.entity;

import jakarta.annotation.Generated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Table("products")
@Getter
@Setter
public class Product {

    @Id
    private Long id;

    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("is_active")
    private Boolean isActive;

    private List<String> tags;  // For the TEXT[] column

    private Map<String, String> metadata;  // For the HSTORE column

    @Column("image_url")
    private String imageUrl;

//    private String location;  // Storing Point as a String, or you can create a custom class for Point

    private BigDecimal weight;

    private String dimensions;  // JSONB data stored as a String
}