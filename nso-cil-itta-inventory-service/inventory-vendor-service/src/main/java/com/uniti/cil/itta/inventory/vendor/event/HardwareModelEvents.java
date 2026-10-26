package com.uniti.cil.itta.inventory.vendor.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.vendor.dto.HardwareModelResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for HardwareModel domain events published to the integration bus. */
public final class HardwareModelEvents {

    public static final String ENTITY_TYPE = "HardwareModel";
    public static final String CREATED = "HardwareModelCreated";
    public static final String UPDATED = "HardwareModelUpdated";
    public static final String DELETED = "HardwareModelDeleted";
    public static final String STATUS_CHANGED = "HardwareModelStatusChanged";

    private HardwareModelEvents() {
    }

    public static DomainEvent created(HardwareModelResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(HardwareModelResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(HardwareModelResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(HardwareModelResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, HardwareModelResponse response, String actor, Map<String, Object> extra) {
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
