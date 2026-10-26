package com.uniti.cil.itta.inventory.discovery.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.discovery.dto.ReconciliationRuleResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ReconciliationRule domain events published to the integration bus. */
public final class ReconciliationRuleEvents {

    public static final String ENTITY_TYPE = "ReconciliationRule";
    public static final String CREATED = "ReconciliationRuleCreated";
    public static final String UPDATED = "ReconciliationRuleUpdated";
    public static final String DELETED = "ReconciliationRuleDeleted";
    public static final String STATUS_CHANGED = "ReconciliationRuleStatusChanged";

    private ReconciliationRuleEvents() {
    }

    public static DomainEvent created(ReconciliationRuleResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ReconciliationRuleResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ReconciliationRuleResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ReconciliationRuleResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ReconciliationRuleResponse response, String actor, Map<String, Object> extra) {
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
