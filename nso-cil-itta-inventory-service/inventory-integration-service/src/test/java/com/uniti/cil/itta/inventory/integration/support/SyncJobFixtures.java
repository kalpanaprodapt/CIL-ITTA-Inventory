package com.uniti.cil.itta.inventory.integration.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for SyncJob. */
public final class SyncJobFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SyncJobFixtures() {
    }

    public static SyncJobCreateRequest createRequest() {
        SyncJobCreateRequest request = new SyncJobCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setEndpointId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setDirection(SyncJobDirection.INBOUND);
        request.setScheduleCron("scheduleCron-1");
        request.setLastRunAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setLastStatus(SyncJobLastStatus.OK);
        request.setOwner("owner-1");
        request.setCriticality(SyncJobCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("SYNCJO-0001");
        request.setName("Sync Job one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static SyncJob entity() {
        SyncJob entity = new SyncJob();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setEndpointId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setDirection(SyncJobDirection.OUTBOUND);
        entity.setScheduleCron("scheduleCron-2");
        entity.setLastRunAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setLastStatus(SyncJobLastStatus.PARTIAL);
        entity.setOwner("owner-2");
        entity.setCriticality(SyncJobCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("SYNCJO-0002");
        entity.setName("Sync Job two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SyncJobResponse response() {
        SyncJobResponse response = new SyncJobResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SYNCJO-0001");
        response.setName("Sync Job one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SyncJobSearchCriteria criteria() {
        SyncJobSearchCriteria criteria = new SyncJobSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
