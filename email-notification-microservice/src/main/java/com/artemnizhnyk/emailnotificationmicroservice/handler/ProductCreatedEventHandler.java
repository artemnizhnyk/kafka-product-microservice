package com.artemnizhnyk.emailnotificationmicroservice.handler;

import com.artemnizhnyk.core.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@KafkaListener(topics = "product-created-events-topic")
@Component
public class ProductCreatedEventHandler {

    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent) {
        log.info("Received event: {}", productCreatedEvent.getTitle());
    }
}
