package com.uniti.cil.itta.inventory.geography.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.geography.dto.BuildingResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Building domain events published to the integration bus. */
public final class BuildingEvents {

    public static final String ENTITY_TYPE = "Building";
    public static final String CREATED = "BuildingCreated";
    public static final String UPDATED = "BuildingUpdated";
    public static final String DELETED = "BuildingDeleted";
    public static final String STATUS_CHANGED = "BuildingStatusChanged";

    private BuildingEvents() {
    }

    public static DomainEvent created(BuildingResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(BuildingResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(BuildingResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(BuildingResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, BuildingResponse response, String actor, Map<String, Object> extra) {
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
