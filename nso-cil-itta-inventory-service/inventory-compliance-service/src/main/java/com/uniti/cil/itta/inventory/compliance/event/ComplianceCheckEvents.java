package com.uniti.cil.itta.inventory.compliance.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.compliance.dto.ComplianceCheckResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ComplianceCheck domain events published to the integration bus. */
public final class ComplianceCheckEvents {

    public static final String ENTITY_TYPE = "ComplianceCheck";
    public static final String CREATED = "ComplianceCheckCreated";
    public static final String UPDATED = "ComplianceCheckUpdated";
    public static final String DELETED = "ComplianceCheckDeleted";
    public static final String STATUS_CHANGED = "ComplianceCheckStatusChanged";

    private ComplianceCheckEvents() {
    }

    public static DomainEvent created(ComplianceCheckResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ComplianceCheckResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ComplianceCheckResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ComplianceCheckResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ComplianceCheckResponse response, String actor, Map<String, Object> extra) {
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
