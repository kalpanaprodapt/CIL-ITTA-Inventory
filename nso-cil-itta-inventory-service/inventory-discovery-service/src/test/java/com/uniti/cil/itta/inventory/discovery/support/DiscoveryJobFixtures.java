package com.uniti.cil.itta.inventory.discovery.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for DiscoveryJob. */
public final class DiscoveryJobFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private DiscoveryJobFixtures() {
    }

    public static DiscoveryJobCreateRequest createRequest() {
        DiscoveryJobCreateRequest request = new DiscoveryJobCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setProfileId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setStartedAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setFinishedAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setJobState(DiscoveryJobJobState.QUEUED);
        request.setAssetsFound(11);
        request.setOwner("owner-1");
        request.setCriticality(DiscoveryJobCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("DISCOV-0001");
        request.setName("Discovery Job one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static DiscoveryJob entity() {
        DiscoveryJob entity = new DiscoveryJob();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setProfileId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setStartedAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setFinishedAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setJobState(DiscoveryJobJobState.RUNNING);
        entity.setAssetsFound(12);
        entity.setOwner("owner-2");
        entity.setCriticality(DiscoveryJobCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("DISCOV-0002");
        entity.setName("Discovery Job two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static DiscoveryJobResponse response() {
        DiscoveryJobResponse response = new DiscoveryJobResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("DISCOV-0001");
        response.setName("Discovery Job one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static DiscoveryJobSearchCriteria criteria() {
        DiscoveryJobSearchCriteria criteria = new DiscoveryJobSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
