package com.uniti.cil.itta.inventory.service.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ServiceDependency. */
public final class ServiceDependencyFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ServiceDependencyFixtures() {
    }

    public static ServiceDependencyCreateRequest createRequest() {
        ServiceDependencyCreateRequest request = new ServiceDependencyCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setServiceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setDependsOnId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setDependencyType(ServiceDependencyDependencyType.HARD);
        request.setImpactScore(new BigDecimal("13.50"));
        request.setCriticality(ServiceDependencyCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("SERVIC-0001");
        request.setName("Service Dependency one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ServiceDependency entity() {
        ServiceDependency entity = new ServiceDependency();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setServiceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setDependsOnId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setDependencyType(ServiceDependencyDependencyType.SOFT);
        entity.setImpactScore(new BigDecimal("14.50"));
        entity.setCriticality(ServiceDependencyCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("SERVIC-0002");
        entity.setName("Service Dependency two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ServiceDependencyResponse response() {
        ServiceDependencyResponse response = new ServiceDependencyResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SERVIC-0001");
        response.setName("Service Dependency one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ServiceDependencySearchCriteria criteria() {
        ServiceDependencySearchCriteria criteria = new ServiceDependencySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
