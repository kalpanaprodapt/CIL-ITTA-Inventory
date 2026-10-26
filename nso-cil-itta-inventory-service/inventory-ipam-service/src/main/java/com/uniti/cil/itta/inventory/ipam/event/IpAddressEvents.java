package com.uniti.cil.itta.inventory.ipam.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.ipam.dto.IpAddressResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for IpAddress domain events published to the integration bus. */
public final class IpAddressEvents {

    public static final String ENTITY_TYPE = "IpAddress";
    public static final String CREATED = "IpAddressCreated";
    public static final String UPDATED = "IpAddressUpdated";
    public static final String DELETED = "IpAddressDeleted";
    public static final String STATUS_CHANGED = "IpAddressStatusChanged";

    private IpAddressEvents() {
    }

    public static DomainEvent created(IpAddressResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(IpAddressResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(IpAddressResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(IpAddressResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, IpAddressResponse response, String actor, Map<String, Object> extra) {
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
