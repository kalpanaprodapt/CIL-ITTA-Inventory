package com.uniti.cil.itta.inventory.common.event;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

public class DomainEvent {

    private final UUID eventId = UUID.randomUUID();
    private final Instant occurredAt = Instant.now();
    private final String eventType;
    private final String entityType;
    private final UUID entityId;
    private final String tenantId;
    private final String actor;
    private final Map<String, Object> payload;

    public DomainEvent(String eventType, String entityType, UUID entityId, String tenantId, String actor,
                       Map<String, Object> payload) {
        this.eventType = eventType;
        this.entityType = entityType;
        this.entityId = entityId;
        this.tenantId = tenantId;
        this.actor = actor;
        this.payload = payload;
    }

    public UUID getEventId() { return eventId; }
    public Instant getOccurredAt() { return occurredAt; }
    public String getEventType() { return eventType; }
    public String getEntityType() { return entityType; }
    public UUID getEntityId() { return entityId; }
    public String getTenantId() { return tenantId; }
    public String getActor() { return actor; }
    public Map<String, Object> getPayload() { return payload; }
}
