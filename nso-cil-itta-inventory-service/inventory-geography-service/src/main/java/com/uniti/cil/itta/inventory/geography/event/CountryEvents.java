package com.uniti.cil.itta.inventory.geography.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.geography.dto.CountryResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Country domain events published to the integration bus. */
public final class CountryEvents {

    public static final String ENTITY_TYPE = "Country";
    public static final String CREATED = "CountryCreated";
    public static final String UPDATED = "CountryUpdated";
    public static final String DELETED = "CountryDeleted";
    public static final String STATUS_CHANGED = "CountryStatusChanged";

    private CountryEvents() {
    }

    public static DomainEvent created(CountryResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(CountryResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(CountryResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(CountryResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, CountryResponse response, String actor, Map<String, Object> extra) {
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
