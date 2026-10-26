package com.uniti.cil.itta.inventory.alarm.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.alarm.dto.AlarmInstanceResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for AlarmInstance domain events published to the integration bus. */
public final class AlarmInstanceEvents {

    public static final String ENTITY_TYPE = "AlarmInstance";
    public static final String CREATED = "AlarmInstanceCreated";
    public static final String UPDATED = "AlarmInstanceUpdated";
    public static final String DELETED = "AlarmInstanceDeleted";
    public static final String STATUS_CHANGED = "AlarmInstanceStatusChanged";

    private AlarmInstanceEvents() {
    }

    public static DomainEvent created(AlarmInstanceResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(AlarmInstanceResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(AlarmInstanceResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(AlarmInstanceResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, AlarmInstanceResponse response, String actor, Map<String, Object> extra) {
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
