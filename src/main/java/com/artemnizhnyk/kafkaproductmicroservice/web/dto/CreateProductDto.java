package com.artemnizhnyk.kafkaproductmicroservice.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateProductDto {

    private String title;
    private BigDecimal price;
    private Integer quantity;
}
