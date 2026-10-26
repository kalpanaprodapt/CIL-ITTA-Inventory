package com.uniti.cil.itta.inventory.geography.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.geography.dto.CityResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for City domain events published to the integration bus. */
public final class CityEvents {

    public static final String ENTITY_TYPE = "City";
    public static final String CREATED = "CityCreated";
    public static final String UPDATED = "CityUpdated";
    public static final String DELETED = "CityDeleted";
    public static final String STATUS_CHANGED = "CityStatusChanged";

    private CityEvents() {
    }

    public static DomainEvent created(CityResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CityResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CityResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CityResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CityResponse response, String actor, Map<String, Object> extra) {
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
