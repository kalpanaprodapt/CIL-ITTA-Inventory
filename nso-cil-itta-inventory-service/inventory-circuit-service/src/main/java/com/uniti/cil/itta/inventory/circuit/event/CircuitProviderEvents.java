package com.uniti.cil.itta.inventory.circuit.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.circuit.dto.CircuitProviderResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for CircuitProvider domain events published to the integration bus. */
public final class CircuitProviderEvents {

    public static final String ENTITY_TYPE = "CircuitProvider";
    public static final String CREATED = "CircuitProviderCreated";
    public static final String UPDATED = "CircuitProviderUpdated";
    public static final String DELETED = "CircuitProviderDeleted";
    public static final String STATUS_CHANGED = "CircuitProviderStatusChanged";

    private CircuitProviderEvents() {
    }

    public static DomainEvent created(CircuitProviderResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CircuitProviderResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CircuitProviderResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CircuitProviderResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CircuitProviderResponse response, String actor, Map<String, Object> extra) {
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
