package com.uniti.cil.itta.inventory.cabling.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for PatchPanel. */
public final class PatchPanelFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PatchPanelFixtures() {
    }

    public static PatchPanelCreateRequest createRequest() {
        PatchPanelCreateRequest request = new PatchPanelCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setRackId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPanelType(PatchPanelPanelType.COPPER);
        request.setPortCount(11);
        request.setLabel("label-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(PatchPanelLifecycleStage.PLAN);
        request.setCode("PATCHP-0001");
        request.setName("Patch Panel one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static PatchPanel entity() {
        PatchPanel entity = new PatchPanel();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setRackId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPanelType(PatchPanelPanelType.FIBER);
        entity.setPortCount(12);
        entity.setLabel("label-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(PatchPanelLifecycleStage.BUILD);
        entity.setCode("PATCHP-0002");
        entity.setName("Patch Panel two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PatchPanelResponse response() {
        PatchPanelResponse response = new PatchPanelResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("PATCHP-0001");
        response.setName("Patch Panel one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PatchPanelSearchCriteria criteria() {
        PatchPanelSearchCriteria criteria = new PatchPanelSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
