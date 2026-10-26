package com.uniti.cil.itta.inventory.virtual.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for K8sCluster. */
public final class K8sClusterFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private K8sClusterFixtures() {
    }

    public static K8sClusterCreateRequest createRequest() {
        K8sClusterCreateRequest request = new K8sClusterCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setClusterName("clusterName-1");
        request.setClusterVersion("clusterVersion-1");
        request.setNodeCount(11);
        request.setCloudAccountId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setRegion("region-1");
        request.setApiEndpoint("apiEndpoint-1");
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(K8sClusterLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setOwner("owner-1");
        request.setCode("K8SCLU-0001");
        request.setName("K8s Cluster one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static K8sCluster entity() {
        K8sCluster entity = new K8sCluster();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setClusterName("clusterName-2");
        entity.setClusterVersion("clusterVersion-2");
        entity.setNodeCount(12);
        entity.setCloudAccountId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setRegion("region-2");
        entity.setApiEndpoint("apiEndpoint-2");
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(K8sClusterLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setOwner("owner-2");
        entity.setCode("K8SCLU-0002");
        entity.setName("K8s Cluster two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static K8sClusterResponse response() {
        K8sClusterResponse response = new K8sClusterResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("K8SCLU-0001");
        response.setName("K8s Cluster one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static K8sClusterSearchCriteria criteria() {
        K8sClusterSearchCriteria criteria = new K8sClusterSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
