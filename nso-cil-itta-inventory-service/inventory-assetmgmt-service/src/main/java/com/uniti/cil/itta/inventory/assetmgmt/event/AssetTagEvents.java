package com.uniti.cil.itta.inventory.assetmgmt.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.assetmgmt.dto.AssetTagResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for AssetTag domain events published to the integration bus. */
public final class AssetTagEvents {

    public static final String ENTITY_TYPE = "AssetTag";
    public static final String CREATED = "AssetTagCreated";
    public static final String UPDATED = "AssetTagUpdated";
    public static final String DELETED = "AssetTagDeleted";
    public static final String STATUS_CHANGED = "AssetTagStatusChanged";

    private AssetTagEvents() {
    }

    public static DomainEvent created(AssetTagResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(AssetTagResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(AssetTagResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(AssetTagResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, AssetTagResponse response, String actor, Map<String, Object> extra) {
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
