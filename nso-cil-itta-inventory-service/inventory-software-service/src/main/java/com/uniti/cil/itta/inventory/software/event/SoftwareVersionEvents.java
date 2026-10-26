package com.uniti.cil.itta.inventory.software.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.software.dto.SoftwareVersionResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for SoftwareVersion domain events published to the integration bus. */
public final class SoftwareVersionEvents {

    public static final String ENTITY_TYPE = "SoftwareVersion";
    public static final String CREATED = "SoftwareVersionCreated";
    public static final String UPDATED = "SoftwareVersionUpdated";
    public static final String DELETED = "SoftwareVersionDeleted";
    public static final String STATUS_CHANGED = "SoftwareVersionStatusChanged";

    private SoftwareVersionEvents() {
    }

    public static DomainEvent created(SoftwareVersionResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SoftwareVersionResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SoftwareVersionResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SoftwareVersionResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SoftwareVersionResponse response, String actor, Map<String, Object> extra) {
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
