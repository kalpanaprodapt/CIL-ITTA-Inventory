package com.uniti.cil.itta.inventory.change.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.change.dto.WorkOrderResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for WorkOrder domain events published to the integration bus. */
public final class WorkOrderEvents {

    public static final String ENTITY_TYPE = "WorkOrder";
    public static final String CREATED = "WorkOrderCreated";
    public static final String UPDATED = "WorkOrderUpdated";
    public static final String DELETED = "WorkOrderDeleted";
    public static final String STATUS_CHANGED = "WorkOrderStatusChanged";

    private WorkOrderEvents() {
    }

    public static DomainEvent created(WorkOrderResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(WorkOrderResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(WorkOrderResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(WorkOrderResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, WorkOrderResponse response, String actor, Map<String, Object> extra) {
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
