package com.uniti.cil.itta.inventory.cabling.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for PatchPort. */
public final class PatchPortFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PatchPortFixtures() {
    }

    public static PatchPortCreateRequest createRequest() {
        PatchPortCreateRequest request = new PatchPortCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPanelId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPortNumber(11);
        request.setConnectedCableId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPolarity("polarity-1");
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(PatchPortLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("PATCHP-0001");
        request.setName("Patch Port one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static PatchPort entity() {
        PatchPort entity = new PatchPort();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPanelId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPortNumber(12);
        entity.setConnectedCableId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPolarity("polarity-2");
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(PatchPortLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("PATCHP-0002");
        entity.setName("Patch Port two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PatchPortResponse response() {
        PatchPortResponse response = new PatchPortResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("PATCHP-0001");
        response.setName("Patch Port one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PatchPortSearchCriteria criteria() {
        PatchPortSearchCriteria criteria = new PatchPortSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
