package com.uniti.cil.itta.inventory.customer.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for CustomerAccount. */
public final class CustomerAccountFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CustomerAccountFixtures() {
    }

    public static CustomerAccountCreateRequest createRequest() {
        CustomerAccountCreateRequest request = new CustomerAccountCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setAccountNumber("accountNumber-1");
        request.setBillingCycle(CustomerAccountBillingCycle.MONTHLY);
        request.setCurrency("currency-1");
        request.setCreditLimit(new BigDecimal("13.50"));
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(CustomerAccountLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("CUSTOM-0001");
        request.setName("Customer Account one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static CustomerAccount entity() {
        CustomerAccount entity = new CustomerAccount();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setAccountNumber("accountNumber-2");
        entity.setBillingCycle(CustomerAccountBillingCycle.QUARTERLY);
        entity.setCurrency("currency-2");
        entity.setCreditLimit(new BigDecimal("14.50"));
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(CustomerAccountLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("CUSTOM-0002");
        entity.setName("Customer Account two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CustomerAccountResponse response() {
        CustomerAccountResponse response = new CustomerAccountResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CUSTOM-0001");
        response.setName("Customer Account one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CustomerAccountSearchCriteria criteria() {
        CustomerAccountSearchCriteria criteria = new CustomerAccountSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
