package com.artemnizhnyk.emailnotificationmicroservice.repository;

import com.artemnizhnyk.emailnotificationmicroservice.persistense.entity.ProcessedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessedEventRepository extends JpaRepository<ProcessedEventEntity, Long> {

    ProcessedEventEntity findByMessageId(final String messageId);
}
