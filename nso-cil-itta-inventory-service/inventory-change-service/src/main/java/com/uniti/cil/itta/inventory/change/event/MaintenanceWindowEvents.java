package com.uniti.cil.itta.inventory.change.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.change.dto.MaintenanceWindowResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for MaintenanceWindow domain events published to the integration bus. */
public final class MaintenanceWindowEvents {

    public static final String ENTITY_TYPE = "MaintenanceWindow";
    public static final String CREATED = "MaintenanceWindowCreated";
    public static final String UPDATED = "MaintenanceWindowUpdated";
    public static final String DELETED = "MaintenanceWindowDeleted";
    public static final String STATUS_CHANGED = "MaintenanceWindowStatusChanged";

    private MaintenanceWindowEvents() {
    }

    public static DomainEvent created(MaintenanceWindowResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(MaintenanceWindowResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(MaintenanceWindowResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(MaintenanceWindowResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, MaintenanceWindowResponse response, String actor, Map<String, Object> extra) {
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
