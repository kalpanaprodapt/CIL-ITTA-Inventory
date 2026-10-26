package com.uniti.cil.itta.inventory.virtual.event;

import com.uniti.cil.itta.inventory.common.event.DomainEvent;
import com.uniti.cil.itta.inventory.virtual.dto.K8sClusterResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/** Factory for K8sCluster domain events published to the integration bus. */
public final class K8sClusterEvents {

    public static final String ENTITY_TYPE = "K8sCluster";
    public static final String CREATED = "K8sClusterCreated";
    public static final String UPDATED = "K8sClusterUpdated";
    public static final String DELETED = "K8sClusterDeleted";
    public static final String STATUS_CHANGED = "K8sClusterStatusChanged";

    private K8sClusterEvents() {
    }

    public static DomainEvent created(K8sClusterResponse response, String actor) {
        return build(CREATED, response, actor, null);
    }

    public static DomainEvent updated(K8sClusterResponse response, String actor) {
        return build(UPDATED, response, actor, null);
    }

    public static DomainEvent deleted(K8sClusterResponse response, String actor) {
        return build(DELETED, response, actor, null);
    }

    public static DomainEvent statusChanged(K8sClusterResponse response, String previous, String actor) {
        Map<String, Object> extra = new LinkedHashMap<>();
        extra.put("previousStatus", previous);
        return build(STATUS_CHANGED, response, actor, extra);
    }

    private static DomainEvent build(String type, K8sClusterResponse response, String actor, Map<String, Object> extra) {
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
