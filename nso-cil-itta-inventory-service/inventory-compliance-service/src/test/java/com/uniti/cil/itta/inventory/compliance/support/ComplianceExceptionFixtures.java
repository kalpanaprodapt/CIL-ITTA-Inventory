package com.uniti.cil.itta.inventory.compliance.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ComplianceException. */
public final class ComplianceExceptionFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ComplianceExceptionFixtures() {
    }

    public static ComplianceExceptionCreateRequest createRequest() {
        ComplianceExceptionCreateRequest request = new ComplianceExceptionCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTargetRef("targetRef-1");
        request.setJustification("justification-1");
        request.setApprovedBy("approvedBy-1");
        request.setExpiresOn(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(ComplianceExceptionLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("COMPLI-0001");
        request.setName("Compliance Exception one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ComplianceException entity() {
        ComplianceException entity = new ComplianceException();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTargetRef("targetRef-2");
        entity.setJustification("justification-2");
        entity.setApprovedBy("approvedBy-2");
        entity.setExpiresOn(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(ComplianceExceptionLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("COMPLI-0002");
        entity.setName("Compliance Exception two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ComplianceExceptionResponse response() {
        ComplianceExceptionResponse response = new ComplianceExceptionResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("COMPLI-0001");
        response.setName("Compliance Exception one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ComplianceExceptionSearchCriteria criteria() {
        ComplianceExceptionSearchCriteria criteria = new ComplianceExceptionSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
