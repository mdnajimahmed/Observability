package com.example.productAggregator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;


@Data
@AllArgsConstructor(staticName = "create")
@NoArgsConstructor
public class ProductDetailsDto {
    private Long id;

    private String name;

    private String description;

    private String category;

    private BigDecimal price;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT")

    private LocalDateTime updatedAt;

    private Boolean isActive;

    private List<String> tags;

    private Map<String, String> metadata;

    private String imageUrl;

    private String location;

    private BigDecimal weight;

    @JsonRawValue
    private String dimensions;
}
