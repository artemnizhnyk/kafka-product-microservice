package com.artemnizhnyk.kafkaproductmicroservice.service;

import com.artemnizhnyk.kafkaproductmicroservice.web.dto.CreateProductDto;

import java.util.concurrent.ExecutionException;

public interface ProductService {

    String createProduct(final CreateProductDto createProductDto) throws ExecutionException, InterruptedException;
}
