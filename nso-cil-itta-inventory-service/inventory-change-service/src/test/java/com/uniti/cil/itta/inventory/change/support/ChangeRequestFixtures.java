package com.uniti.cil.itta.inventory.change.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ChangeRequest. */
public final class ChangeRequestFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ChangeRequestFixtures() {
    }

    public static ChangeRequestCreateRequest createRequest() {
        ChangeRequestCreateRequest request = new ChangeRequestCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setChangeNumber("changeNumber-1");
        request.setChangeType(ChangeRequestChangeType.STANDARD);
        request.setRiskLevel(ChangeRequestRiskLevel.LOW);
        request.setPlannedStart(Instant.parse("2026-02-11T10:15:30Z"));
        request.setPlannedEnd(Instant.parse("2026-02-11T10:15:30Z"));
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(ChangeRequestLifecycleStage.PLAN);
        request.setCode("CHANGE-0001");
        request.setName("Change Request one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ChangeRequest entity() {
        ChangeRequest entity = new ChangeRequest();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setChangeNumber("changeNumber-2");
        entity.setChangeType(ChangeRequestChangeType.NORMAL);
        entity.setRiskLevel(ChangeRequestRiskLevel.MEDIUM);
        entity.setPlannedStart(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setPlannedEnd(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(ChangeRequestLifecycleStage.BUILD);
        entity.setCode("CHANGE-0002");
        entity.setName("Change Request two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ChangeRequestResponse response() {
        ChangeRequestResponse response = new ChangeRequestResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CHANGE-0001");
        response.setName("Change Request one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ChangeRequestSearchCriteria criteria() {
        ChangeRequestSearchCriteria criteria = new ChangeRequestSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
