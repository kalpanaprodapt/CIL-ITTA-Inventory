package com.uniti.cil.itta.inventory.service.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ServiceInstance. */
public final class ServiceInstanceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ServiceInstanceFixtures() {
    }

    public static ServiceInstanceCreateRequest createRequest() {
        ServiceInstanceCreateRequest request = new ServiceInstanceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCatalogItemId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setActivatedOn(LocalDate.of(2026, 2, 11));
        request.setBandwidthMbps(1001L);
        request.setServiceState(ServiceInstanceServiceState.PLANNED);
        request.setLifecycleStage(ServiceInstanceLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(ServiceInstanceCriticality.LOW);
        request.setCode("SERVIC-0001");
        request.setName("Service Instance one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ServiceInstance entity() {
        ServiceInstance entity = new ServiceInstance();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCatalogItemId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setActivatedOn(LocalDate.of(2026, 3, 12));
        entity.setBandwidthMbps(1002L);
        entity.setServiceState(ServiceInstanceServiceState.PROVISIONING);
        entity.setLifecycleStage(ServiceInstanceLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(ServiceInstanceCriticality.MEDIUM);
        entity.setCode("SERVIC-0002");
        entity.setName("Service Instance two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ServiceInstanceResponse response() {
        ServiceInstanceResponse response = new ServiceInstanceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SERVIC-0001");
        response.setName("Service Instance one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ServiceInstanceSearchCriteria criteria() {
        ServiceInstanceSearchCriteria criteria = new ServiceInstanceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
