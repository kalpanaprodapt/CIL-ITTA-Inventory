package com.uniti.cil.itta.inventory.capacity.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.capacity.dto.CapacityPlanResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for CapacityPlan domain events published to the integration bus. */
public final class CapacityPlanEvents {

    public static final String ENTITY_TYPE = "CapacityPlan";
    public static final String CREATED = "CapacityPlanCreated";
    public static final String UPDATED = "CapacityPlanUpdated";
    public static final String DELETED = "CapacityPlanDeleted";
    public static final String STATUS_CHANGED = "CapacityPlanStatusChanged";

    private CapacityPlanEvents() {
    }

    public static DomainEvent created(CapacityPlanResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CapacityPlanResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CapacityPlanResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CapacityPlanResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CapacityPlanResponse response, String actor, Map<String, Object> extra) {
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
