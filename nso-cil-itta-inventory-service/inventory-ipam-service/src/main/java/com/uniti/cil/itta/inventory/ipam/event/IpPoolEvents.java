package com.uniti.cil.itta.inventory.ipam.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.ipam.dto.IpPoolResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for IpPool domain events published to the integration bus. */
public final class IpPoolEvents {

    public static final String ENTITY_TYPE = "IpPool";
    public static final String CREATED = "IpPoolCreated";
    public static final String UPDATED = "IpPoolUpdated";
    public static final String DELETED = "IpPoolDeleted";
    public static final String STATUS_CHANGED = "IpPoolStatusChanged";

    private IpPoolEvents() {
    }

    public static DomainEvent created(IpPoolResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(IpPoolResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(IpPoolResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(IpPoolResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, IpPoolResponse response, String actor, Map<String, Object> extra) {
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
