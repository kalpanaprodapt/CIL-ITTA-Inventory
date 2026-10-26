package com.uniti.cil.itta.inventory.vendor.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for SupportContract. */
public final class SupportContractFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SupportContractFixtures() {
    }

    public static SupportContractCreateRequest createRequest() {
        SupportContractCreateRequest request = new SupportContractCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setContractNumber("contractNumber-1");
        request.setStartDate(LocalDate.of(2026, 2, 11));
        request.setEndDate(LocalDate.of(2026, 2, 11));
        request.setCoverageLevel(SupportContractCoverageLevel.BRONZE);
        request.setAnnualCost(new BigDecimal("13.50"));
        request.setOwner("owner-1");
        request.setCriticality(SupportContractCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("SUPPOR-0001");
        request.setName("Support Contract one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static SupportContract entity() {
        SupportContract entity = new SupportContract();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setContractNumber("contractNumber-2");
        entity.setStartDate(LocalDate.of(2026, 3, 12));
        entity.setEndDate(LocalDate.of(2026, 3, 12));
        entity.setCoverageLevel(SupportContractCoverageLevel.SILVER);
        entity.setAnnualCost(new BigDecimal("14.50"));
        entity.setOwner("owner-2");
        entity.setCriticality(SupportContractCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("SUPPOR-0002");
        entity.setName("Support Contract two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SupportContractResponse response() {
        SupportContractResponse response = new SupportContractResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SUPPOR-0001");
        response.setName("Support Contract one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SupportContractSearchCriteria criteria() {
        SupportContractSearchCriteria criteria = new SupportContractSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
