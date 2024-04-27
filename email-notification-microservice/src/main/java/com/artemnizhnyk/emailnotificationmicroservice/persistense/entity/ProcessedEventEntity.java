package com.artemnizhnyk.emailnotificationmicroservice.persistense.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Table(name = "processed_events")
@Entity
public class ProcessedEventEntity {

    @GeneratedValue
    @Id
    private long id;
    @Column(nullable = false, unique = true)
    private String messageId;
    @Column(nullable = false)
    private String productId;

    public ProcessedEventEntity(final String messageId, final String productId) {
        this.messageId = messageId;
        this.productId = productId;
    }
}
