package com.uniti.cil.itta.inventory.service.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ServiceEndpoint. */
public final class ServiceEndpointFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ServiceEndpointFixtures() {
    }

    public static ServiceEndpointCreateRequest createRequest() {
        ServiceEndpointCreateRequest request = new ServiceEndpointCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setServiceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setEndpointRole(ServiceEndpointEndpointRole.HUB);
        request.setHandoff("handoff-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(ServiceEndpointLifecycleStage.PLAN);
        request.setCode("SERVIC-0001");
        request.setName("Service Endpoint one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ServiceEndpoint entity() {
        ServiceEndpoint entity = new ServiceEndpoint();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setServiceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setEndpointRole(ServiceEndpointEndpointRole.SPOKE);
        entity.setHandoff("handoff-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(ServiceEndpointLifecycleStage.BUILD);
        entity.setCode("SERVIC-0002");
        entity.setName("Service Endpoint two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ServiceEndpointResponse response() {
        ServiceEndpointResponse response = new ServiceEndpointResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SERVIC-0001");
        response.setName("Service Endpoint one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ServiceEndpointSearchCriteria criteria() {
        ServiceEndpointSearchCriteria criteria = new ServiceEndpointSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
