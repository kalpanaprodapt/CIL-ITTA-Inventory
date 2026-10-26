package com.uniti.cil.itta.inventory.circuit.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Circuit. */
public final class CircuitFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CircuitFixtures() {
    }

    public static CircuitCreateRequest createRequest() {
        CircuitCreateRequest request = new CircuitCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCircuitId("circuitId-1");
        request.setCircuitType(CircuitCircuitType.ETHERNET);
        request.setBandwidthMbps(1001L);
        request.setProviderId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setASiteId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setZSiteId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLabels("labels-1");
        request.setLifecycleStage(CircuitLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("CIRCUI-0001");
        request.setName("Circuit one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Circuit entity() {
        Circuit entity = new Circuit();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCircuitId("circuitId-2");
        entity.setCircuitType(CircuitCircuitType.WAVELENGTH);
        entity.setBandwidthMbps(1002L);
        entity.setProviderId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setASiteId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setZSiteId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(CircuitLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("CIRCUI-0002");
        entity.setName("Circuit two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CircuitResponse response() {
        CircuitResponse response = new CircuitResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CIRCUI-0001");
        response.setName("Circuit one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CircuitSearchCriteria criteria() {
        CircuitSearchCriteria criteria = new CircuitSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
