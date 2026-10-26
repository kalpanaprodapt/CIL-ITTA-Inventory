package com.uniti.cil.itta.inventory.port.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Interface. */
public final class InterfaceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private InterfaceFixtures() {
    }

    public static InterfaceCreateRequest createRequest() {
        InterfaceCreateRequest request = new InterfaceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setIfName("ifName-1");
        request.setIfIndex(11);
        request.setMacAddress("macAddress-1");
        request.setMtu(11);
        request.setOperState(InterfaceOperState.UP);
        request.setOwner("owner-1");
        request.setCriticality(InterfaceCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("INTERF-0001");
        request.setName("Interface one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Interface entity() {
        Interface entity = new Interface();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setIfName("ifName-2");
        entity.setIfIndex(12);
        entity.setMacAddress("macAddress-2");
        entity.setMtu(12);
        entity.setOperState(InterfaceOperState.DOWN);
        entity.setOwner("owner-2");
        entity.setCriticality(InterfaceCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("INTERF-0002");
        entity.setName("Interface two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static InterfaceResponse response() {
        InterfaceResponse response = new InterfaceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("INTERF-0001");
        response.setName("Interface one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static InterfaceSearchCriteria criteria() {
        InterfaceSearchCriteria criteria = new InterfaceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
