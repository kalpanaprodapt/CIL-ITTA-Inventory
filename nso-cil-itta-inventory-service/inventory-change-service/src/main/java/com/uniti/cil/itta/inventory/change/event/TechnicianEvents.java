package com.uniti.cil.itta.inventory.change.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.change.dto.TechnicianResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Technician domain events published to the integration bus. */
public final class TechnicianEvents {

    public static final String ENTITY_TYPE = "Technician";
    public static final String CREATED = "TechnicianCreated";
    public static final String UPDATED = "TechnicianUpdated";
    public static final String DELETED = "TechnicianDeleted";
    public static final String STATUS_CHANGED = "TechnicianStatusChanged";

    private TechnicianEvents() {
    }

    public static DomainEvent created(TechnicianResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(TechnicianResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(TechnicianResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(TechnicianResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, TechnicianResponse response, String actor, Map<String, Object> extra) {
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
