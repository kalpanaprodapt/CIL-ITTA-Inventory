package com.uniti.cil.itta.inventory.circuit.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitTerminationResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for CircuitTermination domain events published to the integration bus. */
public final class CircuitTerminationEvents {

    public static final String ENTITY_TYPE = "CircuitTermination";
    public static final String CREATED = "CircuitTerminationCreated";
    public static final String UPDATED = "CircuitTerminationUpdated";
    public static final String DELETED = "CircuitTerminationDeleted";
    public static final String STATUS_CHANGED = "CircuitTerminationStatusChanged";

    private CircuitTerminationEvents() {
    }

    public static DomainEvent created(CircuitTerminationResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CircuitTerminationResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CircuitTerminationResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CircuitTerminationResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CircuitTerminationResponse response, String actor, Map<String, Object> extra) {
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
