package com.uniti.cil.itta.inventory.ipam.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.ipam.dto.IpSubnetResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for IpSubnet domain events published to the integration bus. */
public final class IpSubnetEvents {

    public static final String ENTITY_TYPE = "IpSubnet";
    public static final String CREATED = "IpSubnetCreated";
    public static final String UPDATED = "IpSubnetUpdated";
    public static final String DELETED = "IpSubnetDeleted";
    public static final String STATUS_CHANGED = "IpSubnetStatusChanged";

    private IpSubnetEvents() {
    }

    public static DomainEvent created(IpSubnetResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(IpSubnetResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(IpSubnetResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(IpSubnetResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, IpSubnetResponse response, String actor, Map<String, Object> extra) {
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
