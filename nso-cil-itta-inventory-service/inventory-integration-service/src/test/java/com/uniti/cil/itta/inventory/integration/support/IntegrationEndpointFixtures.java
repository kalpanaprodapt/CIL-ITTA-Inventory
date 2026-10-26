package com.uniti.cil.itta.inventory.integration.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for IntegrationEndpoint. */
public final class IntegrationEndpointFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private IntegrationEndpointFixtures() {
    }

    public static IntegrationEndpointCreateRequest createRequest() {
        IntegrationEndpointCreateRequest request = new IntegrationEndpointCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setEndpointName("endpointName-1");
        request.setTargetSystem("targetSystem-1");
        request.setBaseUrl("baseUrl-1");
        request.setAuthType(IntegrationEndpointAuthType.BASIC);
        request.setTimeoutMs(11);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(IntegrationEndpointCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("INTEGR-0001");
        request.setName("Integration Endpoint one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static IntegrationEndpoint entity() {
        IntegrationEndpoint entity = new IntegrationEndpoint();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setEndpointName("endpointName-2");
        entity.setTargetSystem("targetSystem-2");
        entity.setBaseUrl("baseUrl-2");
        entity.setAuthType(IntegrationEndpointAuthType.OAUTH2);
        entity.setTimeoutMs(12);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(IntegrationEndpointCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("INTEGR-0002");
        entity.setName("Integration Endpoint two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static IntegrationEndpointResponse response() {
        IntegrationEndpointResponse response = new IntegrationEndpointResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("INTEGR-0001");
        response.setName("Integration Endpoint one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static IntegrationEndpointSearchCriteria criteria() {
        IntegrationEndpointSearchCriteria criteria = new IntegrationEndpointSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
