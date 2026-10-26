package com.uniti.cil.itta.inventory.integration.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.integration.dto.SyncMappingResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for SyncMapping domain events published to the integration bus. */
public final class SyncMappingEvents {

    public static final String ENTITY_TYPE = "SyncMapping";
    public static final String CREATED = "SyncMappingCreated";
    public static final String UPDATED = "SyncMappingUpdated";
    public static final String DELETED = "SyncMappingDeleted";
    public static final String STATUS_CHANGED = "SyncMappingStatusChanged";

    private SyncMappingEvents() {
    }

    public static DomainEvent created(SyncMappingResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SyncMappingResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SyncMappingResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SyncMappingResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SyncMappingResponse response, String actor, Map<String, Object> extra) {
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
