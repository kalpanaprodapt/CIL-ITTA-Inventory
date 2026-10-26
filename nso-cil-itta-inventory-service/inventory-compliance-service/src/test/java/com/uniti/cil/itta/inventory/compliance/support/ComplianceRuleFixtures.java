package com.uniti.cil.itta.inventory.compliance.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ComplianceRule. */
public final class ComplianceRuleFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ComplianceRuleFixtures() {
    }

    public static ComplianceRuleCreateRequest createRequest() {
        ComplianceRuleCreateRequest request = new ComplianceRuleCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRuleCode("ruleCode-1");
        request.setFramework(ComplianceRuleFramework.ISO27001);
        request.setSeverity(ComplianceRuleSeverity.LOW);
        request.setExpression("expression-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(ComplianceRuleCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("COMPLI-0001");
        request.setName("Compliance Rule one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ComplianceRule entity() {
        ComplianceRule entity = new ComplianceRule();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRuleCode("ruleCode-2");
        entity.setFramework(ComplianceRuleFramework.SOC2);
        entity.setSeverity(ComplianceRuleSeverity.MEDIUM);
        entity.setExpression("expression-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(ComplianceRuleCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("COMPLI-0002");
        entity.setName("Compliance Rule two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ComplianceRuleResponse response() {
        ComplianceRuleResponse response = new ComplianceRuleResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("COMPLI-0001");
        response.setName("Compliance Rule one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ComplianceRuleSearchCriteria criteria() {
        ComplianceRuleSearchCriteria criteria = new ComplianceRuleSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
