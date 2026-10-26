package com.uniti.cil.itta.inventory.alarm.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for SuppressionRule. */
public final class SuppressionRuleFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SuppressionRuleFixtures() {
    }

    public static SuppressionRuleCreateRequest createRequest() {
        SuppressionRuleCreateRequest request = new SuppressionRuleCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRuleName("ruleName-1");
        request.setMatchExpression("matchExpression-1");
        request.setStartsAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setEndsAt(Instant.parse("2026-02-11T10:15:30Z"));
        request.setReason("reason-1");
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(SuppressionRuleCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("SUPPRE-0001");
        request.setName("Suppression Rule one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static SuppressionRule entity() {
        SuppressionRule entity = new SuppressionRule();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRuleName("ruleName-2");
        entity.setMatchExpression("matchExpression-2");
        entity.setStartsAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setEndsAt(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setReason("reason-2");
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(SuppressionRuleCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("SUPPRE-0002");
        entity.setName("Suppression Rule two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SuppressionRuleResponse response() {
        SuppressionRuleResponse response = new SuppressionRuleResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SUPPRE-0001");
        response.setName("Suppression Rule one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SuppressionRuleSearchCriteria criteria() {
        SuppressionRuleSearchCriteria criteria = new SuppressionRuleSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
