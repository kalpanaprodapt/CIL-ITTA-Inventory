package com.uniti.cil.itta.inventory.customer.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.customer.dto.ContractResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Contract domain events published to the integration bus. */
public final class ContractEvents {

    public static final String ENTITY_TYPE = "Contract";
    public static final String CREATED = "ContractCreated";
    public static final String UPDATED = "ContractUpdated";
    public static final String DELETED = "ContractDeleted";
    public static final String STATUS_CHANGED = "ContractStatusChanged";

    private ContractEvents() {
    }

    public static DomainEvent created(ContractResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ContractResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ContractResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ContractResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ContractResponse response, String actor, Map<String, Object> extra) {
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
