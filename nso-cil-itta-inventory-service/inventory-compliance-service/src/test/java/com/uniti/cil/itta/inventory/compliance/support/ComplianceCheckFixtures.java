package com.uniti.cil.itta.inventory.compliance.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ComplianceCheck. */
public final class ComplianceCheckFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ComplianceCheckFixtures() {
    }

    public static ComplianceCheckCreateRequest createRequest() {
        ComplianceCheckCreateRequest request = new ComplianceCheckCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTargetRef("targetRef-1");
        request.setCheckedAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setResult(ComplianceCheckResult.PASS);
        request.setEvidence("evidence-1");
        request.setCriticality(ComplianceCheckCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("COMPLI-0001");
        request.setName("Compliance Check one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ComplianceCheck entity() {
        ComplianceCheck entity = new ComplianceCheck();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTargetRef("targetRef-2");
        entity.setCheckedAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setResult(ComplianceCheckResult.FAIL);
        entity.setEvidence("evidence-2");
        entity.setCriticality(ComplianceCheckCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("COMPLI-0002");
        entity.setName("Compliance Check two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ComplianceCheckResponse response() {
        ComplianceCheckResponse response = new ComplianceCheckResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("COMPLI-0001");
        response.setName("Compliance Check one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ComplianceCheckSearchCriteria criteria() {
        ComplianceCheckSearchCriteria criteria = new ComplianceCheckSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
