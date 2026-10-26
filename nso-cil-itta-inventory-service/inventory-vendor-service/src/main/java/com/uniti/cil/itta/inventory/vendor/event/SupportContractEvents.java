package com.uniti.cil.itta.inventory.vendor.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.vendor.dto.SupportContractResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for SupportContract domain events published to the integration bus. */
public final class SupportContractEvents {

    public static final String ENTITY_TYPE = "SupportContract";
    public static final String CREATED = "SupportContractCreated";
    public static final String UPDATED = "SupportContractUpdated";
    public static final String DELETED = "SupportContractDeleted";
    public static final String STATUS_CHANGED = "SupportContractStatusChanged";

    private SupportContractEvents() {
    }

    public static DomainEvent created(SupportContractResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(SupportContractResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(SupportContractResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(SupportContractResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, SupportContractResponse response, String actor, Map<String, Object> extra) {
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
