package com.uniti.cil.itta.inventory.common.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Publishes domain events to Kafka after the surrounding transaction commits. */
@Component
public class KafkaEventPublisher implements EventPublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventPublisher.class);

    private final KafkaTemplate<String, Object> kafka;
    private final String topicPrefix;

    public KafkaEventPublisher(KafkaTemplate<String, Object> kafka,
                               @Value("${inventory.events.topic-prefix}") String topicPrefix) {
        this.kafka = kafka;
        this.topicPrefix = topicPrefix;
    }

    @Override
    public void publish(DomainEvent event) {
        String topic = topicPrefix + "." + event.getEntityType().toLowerCase();
        Runnable send = () -> kafka.send(topic, event.getEntityId().toString(), event)
                .whenComplete((res, ex) -> {
                    if (ex != null) {
                        log.error("Failed to publish {} to {}", event.getEventType(), topic, ex);
                    }
                });
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    send.run();
                }
            });
        } else {
            send.run();
        }
    }
}
