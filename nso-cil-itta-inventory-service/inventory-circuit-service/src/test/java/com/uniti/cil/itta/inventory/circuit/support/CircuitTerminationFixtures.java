package com.uniti.cil.itta.inventory.circuit.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for CircuitTermination. */
public final class CircuitTerminationFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CircuitTerminationFixtures() {
    }

    public static CircuitTerminationCreateRequest createRequest() {
        CircuitTerminationCreateRequest request = new CircuitTerminationCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSide(CircuitTerminationSide.A);
        request.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setXconnectId("xconnectId-1");
        request.setPatchInfo("patchInfo-1");
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("CIRCUI-0001");
        request.setName("Circuit Termination one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static CircuitTermination entity() {
        CircuitTermination entity = new CircuitTermination();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCircuitId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSide(CircuitTerminationSide.Z);
        entity.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setXconnectId("xconnectId-2");
        entity.setPatchInfo("patchInfo-2");
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("CIRCUI-0002");
        entity.setName("Circuit Termination two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CircuitTerminationResponse response() {
        CircuitTerminationResponse response = new CircuitTerminationResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CIRCUI-0001");
        response.setName("Circuit Termination one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CircuitTerminationSearchCriteria criteria() {
        CircuitTerminationSearchCriteria criteria = new CircuitTerminationSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
