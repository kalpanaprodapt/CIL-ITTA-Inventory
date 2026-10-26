package com.uniti.cil.itta.inventory.compliance.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for AuditRecord. */
public final class AuditRecordFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private AuditRecordFixtures() {
    }

    public static AuditRecordCreateRequest createRequest() {
        AuditRecordCreateRequest request = new AuditRecordCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setActor("actor-1");
        request.setAction("action-1");
        request.setEntityRef("entityRef-1");
        request.setOccurredAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setBeforeState("beforeState-1");
        request.setAfterState("afterState-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(AuditRecordLifecycleStage.PLAN);
        request.setCode("AUDITR-0001");
        request.setName("Audit Record one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static AuditRecord entity() {
        AuditRecord entity = new AuditRecord();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setActor("actor-2");
        entity.setAction("action-2");
        entity.setEntityRef("entityRef-2");
        entity.setOccurredAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setBeforeState("beforeState-2");
        entity.setAfterState("afterState-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(AuditRecordLifecycleStage.BUILD);
        entity.setCode("AUDITR-0002");
        entity.setName("Audit Record two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static AuditRecordResponse response() {
        AuditRecordResponse response = new AuditRecordResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("AUDITR-0001");
        response.setName("Audit Record one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static AuditRecordSearchCriteria criteria() {
        AuditRecordSearchCriteria criteria = new AuditRecordSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
