package com.artemnizhnyk.emailnotificationmicroservice.handler;

import com.artemnizhnyk.core.event.ProductCreatedEvent;
import com.artemnizhnyk.emailnotificationmicroservice.exception.NonRetryableException;
import com.artemnizhnyk.emailnotificationmicroservice.exception.RetryableException;
import com.artemnizhnyk.emailnotificationmicroservice.persistense.entity.ProcessedEventEntity;
import com.artemnizhnyk.emailnotificationmicroservice.repository.ProcessedEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Slf4j
@RequiredArgsConstructor
@KafkaListener(topics = "product-created-events-topic")
@Component
public class ProductCreatedEventHandler {

    private final ProcessedEventRepository repository;
    private final RestClient restClient;

    @Transactional
    @KafkaHandler
    public void handle(@Payload ProductCreatedEvent productCreatedEvent,
                       @Header("messageId") String messageId,
                       @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        log.info("Received event: {}", productCreatedEvent.getTitle());

        ProcessedEventEntity processedEventEntity = repository.findByMessageId(messageId);

        if (processedEventEntity != null) {
            log.info("Duplicate message id: {}", messageId);
            return;
        }

        try {
            String url = "http://localhost:8090";
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
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new NonRetryableException(e);
        }

        try {
            repository.save(new ProcessedEventEntity(messageId, productCreatedEvent.getProductId()));
        } catch (DataIntegrityViolationException e) {
            log.error(e.getMessage());
            throw new NonRetryableException(e);
        }
    }
}
