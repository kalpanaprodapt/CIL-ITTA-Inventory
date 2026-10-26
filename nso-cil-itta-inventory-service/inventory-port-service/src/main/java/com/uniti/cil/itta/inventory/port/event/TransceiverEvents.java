package com.uniti.cil.itta.inventory.port.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.port.dto.TransceiverResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Transceiver domain events published to the integration bus. */
public final class TransceiverEvents {

    public static final String ENTITY_TYPE = "Transceiver";
    public static final String CREATED = "TransceiverCreated";
    public static final String UPDATED = "TransceiverUpdated";
    public static final String DELETED = "TransceiverDeleted";
    public static final String STATUS_CHANGED = "TransceiverStatusChanged";

    private TransceiverEvents() {
    }

    public static DomainEvent created(TransceiverResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(TransceiverResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(TransceiverResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(TransceiverResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, TransceiverResponse response, String actor, Map<String, Object> extra) {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("id", response.getId());
        payload.put("code", response.getCode());
        payload.put("name", response.getName());
        payload.put("status", response.getStatus());
        payload.put("version", response.getVersion());
        if (extra != null) {
            payload.putAll(extra);
        }
        return new DomainEvent(type, ENTITY_TYPE, response.getId(), response.getTenantId(), actor, payload);
    }
}
