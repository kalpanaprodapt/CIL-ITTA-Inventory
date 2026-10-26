package com.uniti.cil.itta.inventory.software.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.software.dto.LicensePoolResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for LicensePool domain events published to the integration bus. */
public final class LicensePoolEvents {

    public static final String ENTITY_TYPE = "LicensePool";
    public static final String CREATED = "LicensePoolCreated";
    public static final String UPDATED = "LicensePoolUpdated";
    public static final String DELETED = "LicensePoolDeleted";
    public static final String STATUS_CHANGED = "LicensePoolStatusChanged";

    private LicensePoolEvents() {
    }

    public static DomainEvent created(LicensePoolResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(LicensePoolResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(LicensePoolResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(LicensePoolResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, LicensePoolResponse response, String actor, Map<String, Object> extra) {
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
