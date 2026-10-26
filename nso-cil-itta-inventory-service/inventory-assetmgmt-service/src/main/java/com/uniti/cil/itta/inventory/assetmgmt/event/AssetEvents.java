package com.uniti.cil.itta.inventory.assetmgmt.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.assetmgmt.dto.AssetResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for Asset domain events published to the integration bus. */
public final class AssetEvents {

    public static final String ENTITY_TYPE = "Asset";
    public static final String CREATED = "AssetCreated";
    public static final String UPDATED = "AssetUpdated";
    public static final String DELETED = "AssetDeleted";
    public static final String STATUS_CHANGED = "AssetStatusChanged";

    private AssetEvents() {
    }

    public static DomainEvent created(AssetResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(AssetResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(AssetResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(AssetResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, AssetResponse response, String actor, Map<String, Object> extra) {
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
