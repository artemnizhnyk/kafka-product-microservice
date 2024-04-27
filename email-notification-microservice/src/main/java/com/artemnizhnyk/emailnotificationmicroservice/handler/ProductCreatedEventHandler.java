package com.artemnizhnyk.emailnotificationmicroservice.handler;

import com.artemnizhnyk.core.event.ProductCreatedEvent;
import com.artemnizhnyk.emailnotificationmicroservice.exception.NonRetryableException;
import com.artemnizhnyk.emailnotificationmicroservice.exception.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "product-created-events-topic")
@Component
public class ProductCreatedEventHandler {

    private final RestClient restClient;

    @KafkaHandler
    public void handle(ProductCreatedEvent productCreatedEvent) {
        log.info("Received event: {}", productCreatedEvent.getTitle());

        String url = "http://localhost:8090";
        try {
            ResponseEntity<String> response =
                    restClient.get()
                            .uri(url)
                            .accept(MediaType.APPLICATION_JSON)
                            .retrieve()
                            .toEntity(String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Received response: {}", productCreatedEvent.getTitle());
            }
        } catch (ResourceAccessException e) {
            log.error(e.getMessage());
            throw new RetryableException(e);
        } catch (HttpServerErrorException e) {
            log.error(e.getMessage());
            throw new NonRetryableException(e);
        } catch (Exception e) {
            throw new NonRetryableException(e);
        }


    }
}
