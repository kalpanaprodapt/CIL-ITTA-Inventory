package com.uniti.cil.itta.inventory.circuit.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for CircuitOrder. */
public final class CircuitOrderFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CircuitOrderFixtures() {
    }

    public static CircuitOrderCreateRequest createRequest() {
        CircuitOrderCreateRequest request = new CircuitOrderCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setOrderNumber("orderNumber-1");
        request.setOrderedOn(LocalDate.of(2026, 2, 11));
        request.setRequestedDate(LocalDate.of(2026, 2, 11));
        request.setCommittedDate(LocalDate.of(2026, 2, 11));
        request.setOrderStatus(CircuitOrderOrderStatus.DRAFT);
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(CircuitOrderLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("CIRCUI-0001");
        request.setName("Circuit Order one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static CircuitOrder entity() {
        CircuitOrder entity = new CircuitOrder();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setOrderNumber("orderNumber-2");
        entity.setOrderedOn(LocalDate.of(2026, 3, 12));
        entity.setRequestedDate(LocalDate.of(2026, 3, 12));
        entity.setCommittedDate(LocalDate.of(2026, 3, 12));
        entity.setOrderStatus(CircuitOrderOrderStatus.SUBMITTED);
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(CircuitOrderLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("CIRCUI-0002");
        entity.setName("Circuit Order two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CircuitOrderResponse response() {
        CircuitOrderResponse response = new CircuitOrderResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CIRCUI-0001");
        response.setName("Circuit Order one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CircuitOrderSearchCriteria criteria() {
        CircuitOrderSearchCriteria criteria = new CircuitOrderSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
