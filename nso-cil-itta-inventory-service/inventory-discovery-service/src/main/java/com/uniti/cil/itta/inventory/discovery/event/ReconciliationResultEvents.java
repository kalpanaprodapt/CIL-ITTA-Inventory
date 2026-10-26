package com.uniti.cil.itta.inventory.discovery.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.discovery.dto.ReconciliationResultResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ReconciliationResult domain events published to the integration bus. */
public final class ReconciliationResultEvents {

    public static final String ENTITY_TYPE = "ReconciliationResult";
    public static final String CREATED = "ReconciliationResultCreated";
    public static final String UPDATED = "ReconciliationResultUpdated";
    public static final String DELETED = "ReconciliationResultDeleted";
    public static final String STATUS_CHANGED = "ReconciliationResultStatusChanged";

    private ReconciliationResultEvents() {
    }

    public static DomainEvent created(ReconciliationResultResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ReconciliationResultResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ReconciliationResultResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ReconciliationResultResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ReconciliationResultResponse response, String actor, Map<String, Object> extra) {
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
