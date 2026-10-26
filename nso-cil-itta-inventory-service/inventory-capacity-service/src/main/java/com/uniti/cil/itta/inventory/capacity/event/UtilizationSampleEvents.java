package com.uniti.cil.itta.inventory.capacity.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.capacity.dto.UtilizationSampleResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for UtilizationSample domain events published to the integration bus. */
public final class UtilizationSampleEvents {

    public static final String ENTITY_TYPE = "UtilizationSample";
    public static final String CREATED = "UtilizationSampleCreated";
    public static final String UPDATED = "UtilizationSampleUpdated";
    public static final String DELETED = "UtilizationSampleDeleted";
    public static final String STATUS_CHANGED = "UtilizationSampleStatusChanged";

    private UtilizationSampleEvents() {
    }

    public static DomainEvent created(UtilizationSampleResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(UtilizationSampleResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(UtilizationSampleResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(UtilizationSampleResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, UtilizationSampleResponse response, String actor, Map<String, Object> extra) {
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
