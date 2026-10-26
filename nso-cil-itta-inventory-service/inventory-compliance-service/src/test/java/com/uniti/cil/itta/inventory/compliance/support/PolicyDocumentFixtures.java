package com.uniti.cil.itta.inventory.compliance.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for PolicyDocument. */
public final class PolicyDocumentFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PolicyDocumentFixtures() {
    }

    public static PolicyDocumentCreateRequest createRequest() {
        PolicyDocumentCreateRequest request = new PolicyDocumentCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setTitle("title-1");
        request.setDocVersion("docVersion-1");
        request.setEffectiveDate(LocalDate.of(2026, 2, 11));
        request.setOwner("owner-1");
        request.setDocumentUrl("documentUrl-1");
        request.setLifecycleStage(PolicyDocumentLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCriticality(PolicyDocumentCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("POLICY-0001");
        request.setName("Policy Document one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static PolicyDocument entity() {
        PolicyDocument entity = new PolicyDocument();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setTitle("title-2");
        entity.setDocVersion("docVersion-2");
        entity.setEffectiveDate(LocalDate.of(2026, 3, 12));
        entity.setOwner("owner-2");
        entity.setDocumentUrl("documentUrl-2");
        entity.setLifecycleStage(PolicyDocumentLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCriticality(PolicyDocumentCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("POLICY-0002");
        entity.setName("Policy Document two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PolicyDocumentResponse response() {
        PolicyDocumentResponse response = new PolicyDocumentResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("POLICY-0001");
        response.setName("Policy Document one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PolicyDocumentSearchCriteria criteria() {
        PolicyDocumentSearchCriteria criteria = new PolicyDocumentSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
