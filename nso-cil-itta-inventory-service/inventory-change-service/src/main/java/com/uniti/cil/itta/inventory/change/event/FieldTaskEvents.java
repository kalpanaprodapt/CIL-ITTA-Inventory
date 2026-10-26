package com.uniti.cil.itta.inventory.change.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.change.dto.FieldTaskResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for FieldTask domain events published to the integration bus. */
public final class FieldTaskEvents {

    public static final String ENTITY_TYPE = "FieldTask";
    public static final String CREATED = "FieldTaskCreated";
    public static final String UPDATED = "FieldTaskUpdated";
    public static final String DELETED = "FieldTaskDeleted";
    public static final String STATUS_CHANGED = "FieldTaskStatusChanged";

    private FieldTaskEvents() {
    }

    public static DomainEvent created(FieldTaskResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(FieldTaskResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(FieldTaskResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(FieldTaskResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, FieldTaskResponse response, String actor, Map<String, Object> extra) {
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
