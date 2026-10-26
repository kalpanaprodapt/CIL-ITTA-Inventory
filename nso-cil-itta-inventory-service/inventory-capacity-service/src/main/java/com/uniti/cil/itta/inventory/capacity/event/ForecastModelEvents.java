package com.uniti.cil.itta.inventory.capacity.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.capacity.dto.ForecastModelResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ForecastModel domain events published to the integration bus. */
public final class ForecastModelEvents {

    public static final String ENTITY_TYPE = "ForecastModel";
    public static final String CREATED = "ForecastModelCreated";
    public static final String UPDATED = "ForecastModelUpdated";
    public static final String DELETED = "ForecastModelDeleted";
    public static final String STATUS_CHANGED = "ForecastModelStatusChanged";

    private ForecastModelEvents() {
    }

    public static DomainEvent created(ForecastModelResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ForecastModelResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ForecastModelResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ForecastModelResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ForecastModelResponse response, String actor, Map<String, Object> extra) {
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
