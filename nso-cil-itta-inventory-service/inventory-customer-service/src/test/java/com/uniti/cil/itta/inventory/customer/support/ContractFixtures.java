package com.uniti.cil.itta.inventory.customer.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Contract. */
public final class ContractFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ContractFixtures() {
    }

    public static ContractCreateRequest createRequest() {
        ContractCreateRequest request = new ContractCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setContractNumber("contractNumber-1");
        request.setStartDate(LocalDate.of(2026, 2, 11));
        request.setEndDate(LocalDate.of(2026, 2, 11));
        request.setTermMonths(11);
        request.setAutoRenew(Boolean.TRUE);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(ContractCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("CONTRA-0001");
        request.setName("Contract one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Contract entity() {
        Contract entity = new Contract();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setContractNumber("contractNumber-2");
        entity.setStartDate(LocalDate.of(2026, 3, 12));
        entity.setEndDate(LocalDate.of(2026, 3, 12));
        entity.setTermMonths(12);
        entity.setAutoRenew(Boolean.FALSE);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(ContractCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("CONTRA-0002");
        entity.setName("Contract two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ContractResponse response() {
        ContractResponse response = new ContractResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CONTRA-0001");
        response.setName("Contract one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ContractSearchCriteria criteria() {
        ContractSearchCriteria criteria = new ContractSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
