package com.artemnizhnyk.kafkaproductmicroservice.web.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@AllArgsConstructor
@Data
public class ErrorMessage {

    private Instant timestamp;
    private String message;
}
