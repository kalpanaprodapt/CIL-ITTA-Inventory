package com.uniti.cil.itta.inventory.service.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ServiceOrder. */
public final class ServiceOrderFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ServiceOrderFixtures() {
    }

    public static ServiceOrderCreateRequest createRequest() {
        ServiceOrderCreateRequest request = new ServiceOrderCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setOrderType(ServiceOrderOrderType.NEW);
        request.setRequestedOn(LocalDate.of(2026, 2, 11));
        request.setTargetDate(LocalDate.of(2026, 2, 11));
        request.setPriority(11);
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(ServiceOrderCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("SERVIC-0001");
        request.setName("Service Order one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ServiceOrder entity() {
        ServiceOrder entity = new ServiceOrder();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setOrderType(ServiceOrderOrderType.CHANGE);
        entity.setRequestedOn(LocalDate.of(2026, 3, 12));
        entity.setTargetDate(LocalDate.of(2026, 3, 12));
        entity.setPriority(12);
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(ServiceOrderCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("SERVIC-0002");
        entity.setName("Service Order two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ServiceOrderResponse response() {
        ServiceOrderResponse response = new ServiceOrderResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SERVIC-0001");
        response.setName("Service Order one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ServiceOrderSearchCriteria criteria() {
        ServiceOrderSearchCriteria criteria = new ServiceOrderSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
