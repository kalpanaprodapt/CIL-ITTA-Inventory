package com.uniti.cil.itta.inventory.integration.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for SyncMapping. */
public final class SyncMappingFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SyncMappingFixtures() {
    }

    public static SyncMappingCreateRequest createRequest() {
        SyncMappingCreateRequest request = new SyncMappingCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSourceField("sourceField-1");
        request.setTargetField("targetField-1");
        request.setTransform("transform-1");
        request.setRequired(Boolean.TRUE);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("SYNCMA-0001");
        request.setName("Sync Mapping one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static SyncMapping entity() {
        SyncMapping entity = new SyncMapping();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSourceField("sourceField-2");
        entity.setTargetField("targetField-2");
        entity.setTransform("transform-2");
        entity.setRequired(Boolean.FALSE);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("SYNCMA-0002");
        entity.setName("Sync Mapping two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SyncMappingResponse response() {
        SyncMappingResponse response = new SyncMappingResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SYNCMA-0001");
        response.setName("Sync Mapping one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SyncMappingSearchCriteria criteria() {
        SyncMappingSearchCriteria criteria = new SyncMappingSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
