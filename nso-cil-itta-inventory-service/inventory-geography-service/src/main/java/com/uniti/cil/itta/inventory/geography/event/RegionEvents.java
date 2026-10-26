package com.uniti.cil.itta.inventory.geography.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.geography.dto.RegionResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Region domain events published to the integration bus. */
public final class RegionEvents {

    public static final String ENTITY_TYPE = "Region";
    public static final String CREATED = "RegionCreated";
    public static final String UPDATED = "RegionUpdated";
    public static final String DELETED = "RegionDeleted";
    public static final String STATUS_CHANGED = "RegionStatusChanged";

    private RegionEvents() {
    }

    public static DomainEvent created(RegionResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(RegionResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(RegionResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(RegionResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, RegionResponse response, String actor, Map<String, Object> extra) {
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
