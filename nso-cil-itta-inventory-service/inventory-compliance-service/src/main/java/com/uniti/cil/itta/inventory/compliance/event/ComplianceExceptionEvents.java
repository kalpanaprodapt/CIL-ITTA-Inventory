package com.uniti.cil.itta.inventory.compliance.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.compliance.dto.ComplianceExceptionResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ComplianceException domain events published to the integration bus. */
public final class ComplianceExceptionEvents {

    public static final String ENTITY_TYPE = "ComplianceException";
    public static final String CREATED = "ComplianceExceptionCreated";
    public static final String UPDATED = "ComplianceExceptionUpdated";
    public static final String DELETED = "ComplianceExceptionDeleted";
    public static final String STATUS_CHANGED = "ComplianceExceptionStatusChanged";

    private ComplianceExceptionEvents() {
    }

    public static DomainEvent created(ComplianceExceptionResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ComplianceExceptionResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ComplianceExceptionResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ComplianceExceptionResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ComplianceExceptionResponse response, String actor, Map<String, Object> extra) {
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
