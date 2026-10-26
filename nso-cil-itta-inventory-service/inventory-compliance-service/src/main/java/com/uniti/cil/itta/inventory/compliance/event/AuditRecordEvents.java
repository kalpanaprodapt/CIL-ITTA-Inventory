package com.uniti.cil.itta.inventory.compliance.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.compliance.dto.AuditRecordResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for AuditRecord domain events published to the integration bus. */
public final class AuditRecordEvents {

    public static final String ENTITY_TYPE = "AuditRecord";
    public static final String CREATED = "AuditRecordCreated";
    public static final String UPDATED = "AuditRecordUpdated";
    public static final String DELETED = "AuditRecordDeleted";
    public static final String STATUS_CHANGED = "AuditRecordStatusChanged";

    private AuditRecordEvents() {
    }

    public static DomainEvent created(AuditRecordResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(AuditRecordResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(AuditRecordResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(AuditRecordResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, AuditRecordResponse response, String actor, Map<String, Object> extra) {
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
