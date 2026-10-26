package com.uniti.cil.itta.inventory.change.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.change.dto.ChangeRequestResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ChangeRequest domain events published to the integration bus. */
public final class ChangeRequestEvents {

    public static final String ENTITY_TYPE = "ChangeRequest";
    public static final String CREATED = "ChangeRequestCreated";
    public static final String UPDATED = "ChangeRequestUpdated";
    public static final String DELETED = "ChangeRequestDeleted";
    public static final String STATUS_CHANGED = "ChangeRequestStatusChanged";

    private ChangeRequestEvents() {
    }

    public static DomainEvent created(ChangeRequestResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ChangeRequestResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ChangeRequestResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ChangeRequestResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ChangeRequestResponse response, String actor, Map<String, Object> extra) {
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
