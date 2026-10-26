package com.uniti.cil.itta.inventory.discovery.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ReconciliationResult. */
public final class ReconciliationResultFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ReconciliationResultFixtures() {
    }

    public static ReconciliationResultCreateRequest createRequest() {
        ReconciliationResultCreateRequest request = new ReconciliationResultCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setOutcome(ReconciliationResultOutcome.MATCHED);
        request.setDiffSummary("diffSummary-1");
        request.setResolvedBy("resolvedBy-1");
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(ReconciliationResultCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("RECONC-0001");
        request.setName("Reconciliation Result one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ReconciliationResult entity() {
        ReconciliationResult entity = new ReconciliationResult();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setRuleId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setOutcome(ReconciliationResultOutcome.CREATED);
        entity.setDiffSummary("diffSummary-2");
        entity.setResolvedBy("resolvedBy-2");
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(ReconciliationResultCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("RECONC-0002");
        entity.setName("Reconciliation Result two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ReconciliationResultResponse response() {
        ReconciliationResultResponse response = new ReconciliationResultResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("RECONC-0001");
        response.setName("Reconciliation Result one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ReconciliationResultSearchCriteria criteria() {
        ReconciliationResultSearchCriteria criteria = new ReconciliationResultSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
