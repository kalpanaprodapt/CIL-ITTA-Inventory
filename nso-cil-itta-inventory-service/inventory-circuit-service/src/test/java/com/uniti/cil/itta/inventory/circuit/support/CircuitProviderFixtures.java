package com.uniti.cil.itta.inventory.circuit.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for CircuitProvider. */
public final class CircuitProviderFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CircuitProviderFixtures() {
    }

    public static CircuitProviderCreateRequest createRequest() {
        CircuitProviderCreateRequest request = new CircuitProviderCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setProviderName("providerName-1");
        request.setAccountNumber("accountNumber-1");
        request.setNoc("noc-1");
        request.setEscalationEmail("escalationEmail-1");
        request.setProviderType(CircuitProviderProviderType.TIER1);
        request.setOwner("owner-1");
        request.setCriticality(CircuitProviderCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("CIRCUI-0001");
        request.setName("Circuit Provider one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static CircuitProvider entity() {
        CircuitProvider entity = new CircuitProvider();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setProviderName("providerName-2");
        entity.setAccountNumber("accountNumber-2");
        entity.setNoc("noc-2");
        entity.setEscalationEmail("escalationEmail-2");
        entity.setProviderType(CircuitProviderProviderType.TIER2);
        entity.setOwner("owner-2");
        entity.setCriticality(CircuitProviderCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("CIRCUI-0002");
        entity.setName("Circuit Provider two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CircuitProviderResponse response() {
        CircuitProviderResponse response = new CircuitProviderResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CIRCUI-0001");
        response.setName("Circuit Provider one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CircuitProviderSearchCriteria criteria() {
        CircuitProviderSearchCriteria criteria = new CircuitProviderSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
