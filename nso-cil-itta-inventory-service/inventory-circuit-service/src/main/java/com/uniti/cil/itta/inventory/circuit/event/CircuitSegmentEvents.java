package com.uniti.cil.itta.inventory.circuit.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitSegmentResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for CircuitSegment domain events published to the integration bus. */
public final class CircuitSegmentEvents {

    public static final String ENTITY_TYPE = "CircuitSegment";
    public static final String CREATED = "CircuitSegmentCreated";
    public static final String UPDATED = "CircuitSegmentUpdated";
    public static final String DELETED = "CircuitSegmentDeleted";
    public static final String STATUS_CHANGED = "CircuitSegmentStatusChanged";

    private CircuitSegmentEvents() {
    }

    public static DomainEvent created(CircuitSegmentResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CircuitSegmentResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CircuitSegmentResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CircuitSegmentResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CircuitSegmentResponse response, String actor, Map<String, Object> extra) {
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
