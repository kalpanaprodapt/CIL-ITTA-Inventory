package com.uniti.cil.itta.inventory.software.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Patch. */
public final class PatchFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PatchFixtures() {
    }

    public static PatchCreateRequest createRequest() {
        PatchCreateRequest request = new PatchCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVersionId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPatchId("patchId-1");
        request.setSeverity(PatchSeverity.LOW);
        request.setReleasedOn(LocalDate.of(2026, 2, 11));
        request.setRebootRequired(Boolean.TRUE);
        request.setCriticality(PatchCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("PATCH-0001");
        request.setName("Patch one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Patch entity() {
        Patch entity = new Patch();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVersionId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPatchId("patchId-2");
        entity.setSeverity(PatchSeverity.MEDIUM);
        entity.setReleasedOn(LocalDate.of(2026, 3, 12));
        entity.setRebootRequired(Boolean.FALSE);
        entity.setCriticality(PatchCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("PATCH-0002");
        entity.setName("Patch two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PatchResponse response() {
        PatchResponse response = new PatchResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("PATCH-0001");
        response.setName("Patch one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PatchSearchCriteria criteria() {
        PatchSearchCriteria criteria = new PatchSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
