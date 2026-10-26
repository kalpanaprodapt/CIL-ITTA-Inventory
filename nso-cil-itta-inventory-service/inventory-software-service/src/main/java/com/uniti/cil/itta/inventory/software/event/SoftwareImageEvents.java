package com.uniti.cil.itta.inventory.software.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.software.dto.SoftwareImageResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for SoftwareImage domain events published to the integration bus. */
public final class SoftwareImageEvents {

    public static final String ENTITY_TYPE = "SoftwareImage";
    public static final String CREATED = "SoftwareImageCreated";
    public static final String UPDATED = "SoftwareImageUpdated";
    public static final String DELETED = "SoftwareImageDeleted";
    public static final String STATUS_CHANGED = "SoftwareImageStatusChanged";

    private SoftwareImageEvents() {
    }

    public static DomainEvent created(SoftwareImageResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SoftwareImageResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SoftwareImageResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SoftwareImageResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SoftwareImageResponse response, String actor, Map<String, Object> extra) {
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
