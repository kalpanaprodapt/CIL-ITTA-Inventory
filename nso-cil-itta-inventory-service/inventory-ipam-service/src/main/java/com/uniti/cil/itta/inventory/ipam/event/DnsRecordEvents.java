package com.uniti.cil.itta.inventory.ipam.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.ipam.dto.DnsRecordResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for DnsRecord domain events published to the integration bus. */
public final class DnsRecordEvents {

    public static final String ENTITY_TYPE = "DnsRecord";
    public static final String CREATED = "DnsRecordCreated";
    public static final String UPDATED = "DnsRecordUpdated";
    public static final String DELETED = "DnsRecordDeleted";
    public static final String STATUS_CHANGED = "DnsRecordStatusChanged";

    private DnsRecordEvents() {
    }

    public static DomainEvent created(DnsRecordResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(DnsRecordResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(DnsRecordResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(DnsRecordResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, DnsRecordResponse response, String actor, Map<String, Object> extra) {
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
