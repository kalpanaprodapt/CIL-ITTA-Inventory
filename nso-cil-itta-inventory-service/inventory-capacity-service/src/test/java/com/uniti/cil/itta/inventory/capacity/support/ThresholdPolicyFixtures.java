package com.uniti.cil.itta.inventory.capacity.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ThresholdPolicy. */
public final class ThresholdPolicyFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ThresholdPolicyFixtures() {
    }

    public static ThresholdPolicyCreateRequest createRequest() {
        ThresholdPolicyCreateRequest request = new ThresholdPolicyCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPolicyName("policyName-1");
        request.setMetric("metric-1");
        request.setWarnPct(new BigDecimal("13.50"));
        request.setCritPct(new BigDecimal("13.50"));
        request.setEvaluationWindowMinutes(11);
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(ThresholdPolicyCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("THRESH-0001");
        request.setName("Threshold Policy one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ThresholdPolicy entity() {
        ThresholdPolicy entity = new ThresholdPolicy();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPolicyName("policyName-2");
        entity.setMetric("metric-2");
        entity.setWarnPct(new BigDecimal("14.50"));
        entity.setCritPct(new BigDecimal("14.50"));
        entity.setEvaluationWindowMinutes(12);
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(ThresholdPolicyCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("THRESH-0002");
        entity.setName("Threshold Policy two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ThresholdPolicyResponse response() {
        ThresholdPolicyResponse response = new ThresholdPolicyResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("THRESH-0001");
        response.setName("Threshold Policy one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ThresholdPolicySearchCriteria criteria() {
        ThresholdPolicySearchCriteria criteria = new ThresholdPolicySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
