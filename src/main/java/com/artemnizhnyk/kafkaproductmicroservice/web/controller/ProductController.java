package com.artemnizhnyk.kafkaproductmicroservice.web.controller;

import com.artemnizhnyk.kafkaproductmicroservice.service.ProductService;
import com.artemnizhnyk.kafkaproductmicroservice.web.dto.CreateProductDto;
import com.artemnizhnyk.kafkaproductmicroservice.web.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @PostMapping
    ResponseEntity<Object> createProduct(@RequestBody final CreateProductDto createProductDto) {
        String productId;
        try {
            productId = productService.createProduct(createProductDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessage(Instant.now(), e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
