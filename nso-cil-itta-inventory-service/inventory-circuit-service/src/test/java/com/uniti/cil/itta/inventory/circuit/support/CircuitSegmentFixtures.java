package com.uniti.cil.itta.inventory.circuit.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for CircuitSegment. */
public final class CircuitSegmentFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CircuitSegmentFixtures() {
    }

    public static CircuitSegmentCreateRequest createRequest() {
        CircuitSegmentCreateRequest request = new CircuitSegmentCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSegmentOrder(11);
        request.setATerminationId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setZTerminationId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLatencyMs(new BigDecimal("13.50"));
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(CircuitSegmentCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("CIRCUI-0001");
        request.setName("Circuit Segment one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static CircuitSegment entity() {
        CircuitSegment entity = new CircuitSegment();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSegmentOrder(12);
        entity.setATerminationId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setZTerminationId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLatencyMs(new BigDecimal("14.50"));
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(CircuitSegmentCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("CIRCUI-0002");
        entity.setName("Circuit Segment two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CircuitSegmentResponse response() {
        CircuitSegmentResponse response = new CircuitSegmentResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CIRCUI-0001");
        response.setName("Circuit Segment one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CircuitSegmentSearchCriteria criteria() {
        CircuitSegmentSearchCriteria criteria = new CircuitSegmentSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
