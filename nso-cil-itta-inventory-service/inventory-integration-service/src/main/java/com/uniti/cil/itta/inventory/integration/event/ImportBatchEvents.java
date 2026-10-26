package com.uniti.cil.itta.inventory.integration.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.integration.dto.ImportBatchResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for ImportBatch domain events published to the integration bus. */
public final class ImportBatchEvents {

    public static final String ENTITY_TYPE = "ImportBatch";
    public static final String CREATED = "ImportBatchCreated";
    public static final String UPDATED = "ImportBatchUpdated";
    public static final String DELETED = "ImportBatchDeleted";
    public static final String STATUS_CHANGED = "ImportBatchStatusChanged";

    private ImportBatchEvents() {
    }

    public static DomainEvent created(ImportBatchResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(ImportBatchResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(ImportBatchResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(ImportBatchResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, ImportBatchResponse response, String actor, Map<String, Object> extra) {
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
