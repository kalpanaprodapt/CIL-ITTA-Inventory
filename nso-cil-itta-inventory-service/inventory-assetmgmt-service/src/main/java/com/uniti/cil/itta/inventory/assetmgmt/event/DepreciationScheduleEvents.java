package com.uniti.cil.itta.inventory.assetmgmt.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.assetmgmt.dto.DepreciationScheduleResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for DepreciationSchedule domain events published to the integration bus. */
public final class DepreciationScheduleEvents {

    public static final String ENTITY_TYPE = "DepreciationSchedule";
    public static final String CREATED = "DepreciationScheduleCreated";
    public static final String UPDATED = "DepreciationScheduleUpdated";
    public static final String DELETED = "DepreciationScheduleDeleted";
    public static final String STATUS_CHANGED = "DepreciationScheduleStatusChanged";

    private DepreciationScheduleEvents() {
    }

    public static DomainEvent created(DepreciationScheduleResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(DepreciationScheduleResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(DepreciationScheduleResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(DepreciationScheduleResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, DepreciationScheduleResponse response, String actor, Map<String, Object> extra) {
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
