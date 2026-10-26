package com.uniti.cil.itta.inventory.virtual.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.virtual.dto.HypervisorResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Hypervisor domain events published to the integration bus. */
public final class HypervisorEvents {

    public static final String ENTITY_TYPE = "Hypervisor";
    public static final String CREATED = "HypervisorCreated";
    public static final String UPDATED = "HypervisorUpdated";
    public static final String DELETED = "HypervisorDeleted";
    public static final String STATUS_CHANGED = "HypervisorStatusChanged";

    private HypervisorEvents() {
    }

    public static DomainEvent created(HypervisorResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(HypervisorResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(HypervisorResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(HypervisorResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, HypervisorResponse response, String actor, Map<String, Object> extra) {
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
