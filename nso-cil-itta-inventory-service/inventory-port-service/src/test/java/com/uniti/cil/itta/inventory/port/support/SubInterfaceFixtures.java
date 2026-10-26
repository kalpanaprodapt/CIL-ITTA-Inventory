package com.uniti.cil.itta.inventory.port.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for SubInterface. */
public final class SubInterfaceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SubInterfaceFixtures() {
    }

    public static SubInterfaceCreateRequest createRequest() {
        SubInterfaceCreateRequest request = new SubInterfaceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setVlanTag(11);
        request.setEncapsulation("encapsulation-1");
        request.setIpv4Address("ipv4Address-1");
        request.setIpv6Address("ipv6Address-1");
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("SUBINT-0001");
        request.setName("Sub Interface one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static SubInterface entity() {
        SubInterface entity = new SubInterface();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setVlanTag(12);
        entity.setEncapsulation("encapsulation-2");
        entity.setIpv4Address("ipv4Address-2");
        entity.setIpv6Address("ipv6Address-2");
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("SUBINT-0002");
        entity.setName("Sub Interface two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SubInterfaceResponse response() {
        SubInterfaceResponse response = new SubInterfaceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SUBINT-0001");
        response.setName("Sub Interface one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SubInterfaceSearchCriteria criteria() {
        SubInterfaceSearchCriteria criteria = new SubInterfaceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
