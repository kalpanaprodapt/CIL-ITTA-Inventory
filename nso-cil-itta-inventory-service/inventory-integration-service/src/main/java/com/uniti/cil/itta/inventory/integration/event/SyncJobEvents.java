package com.uniti.cil.itta.inventory.integration.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.integration.dto.SyncJobResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for SyncJob domain events published to the integration bus. */
public final class SyncJobEvents {

    public static final String ENTITY_TYPE = "SyncJob";
    public static final String CREATED = "SyncJobCreated";
    public static final String UPDATED = "SyncJobUpdated";
    public static final String DELETED = "SyncJobDeleted";
    public static final String STATUS_CHANGED = "SyncJobStatusChanged";

    private SyncJobEvents() {
    }

    public static DomainEvent created(SyncJobResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SyncJobResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SyncJobResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SyncJobResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SyncJobResponse response, String actor, Map<String, Object> extra) {
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
