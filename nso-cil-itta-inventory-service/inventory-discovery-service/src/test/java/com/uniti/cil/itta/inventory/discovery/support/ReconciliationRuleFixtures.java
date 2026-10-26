package com.uniti.cil.itta.inventory.discovery.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ReconciliationRule. */
public final class ReconciliationRuleFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ReconciliationRuleFixtures() {
    }

    public static ReconciliationRuleCreateRequest createRequest() {
        ReconciliationRuleCreateRequest request = new ReconciliationRuleCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRuleName("ruleName-1");
        request.setMatchKey("matchKey-1");
        request.setAction(ReconciliationRuleAction.MERGE);
        request.setPriority(11);
        request.setExpression("expression-1");
        request.setLabels("labels-1");
        request.setLifecycleStage(ReconciliationRuleLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("RECONC-0001");
        request.setName("Reconciliation Rule one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ReconciliationRule entity() {
        ReconciliationRule entity = new ReconciliationRule();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRuleName("ruleName-2");
        entity.setMatchKey("matchKey-2");
        entity.setAction(ReconciliationRuleAction.CREATE);
        entity.setPriority(12);
        entity.setExpression("expression-2");
        entity.setLabels("labels-2");
        entity.setLifecycleStage(ReconciliationRuleLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("RECONC-0002");
        entity.setName("Reconciliation Rule two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ReconciliationRuleResponse response() {
        ReconciliationRuleResponse response = new ReconciliationRuleResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("RECONC-0001");
        response.setName("Reconciliation Rule one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ReconciliationRuleSearchCriteria criteria() {
        ReconciliationRuleSearchCriteria criteria = new ReconciliationRuleSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
