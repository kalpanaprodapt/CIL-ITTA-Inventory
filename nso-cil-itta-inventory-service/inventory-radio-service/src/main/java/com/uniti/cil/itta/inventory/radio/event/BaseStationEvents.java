package com.uniti.cil.itta.inventory.radio.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.radio.dto.BaseStationResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for BaseStation domain events published to the integration bus. */
public final class BaseStationEvents {

    public static final String ENTITY_TYPE = "BaseStation";
    public static final String CREATED = "BaseStationCreated";
    public static final String UPDATED = "BaseStationUpdated";
    public static final String DELETED = "BaseStationDeleted";
    public static final String STATUS_CHANGED = "BaseStationStatusChanged";

    private BaseStationEvents() {
    }

    public static DomainEvent created(BaseStationResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(BaseStationResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(BaseStationResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(BaseStationResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, BaseStationResponse response, String actor, Map<String, Object> extra) {
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
