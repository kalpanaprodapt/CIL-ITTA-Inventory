package com.uniti.cil.itta.inventory.customer.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Customer. */
public final class CustomerFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CustomerFixtures() {
    }

    public static CustomerCreateRequest createRequest() {
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setLegalName("legalName-1");
        request.setSegment(CustomerSegment.ENTERPRISE);
        request.setTaxId("taxId-1");
        request.setIndustry("industry-1");
        request.setAccountManager("accountManager-1");
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("CUSTOM-0001");
        request.setName("Customer one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Customer entity() {
        Customer entity = new Customer();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setLegalName("legalName-2");
        entity.setSegment(CustomerSegment.WHOLESALE);
        entity.setTaxId("taxId-2");
        entity.setIndustry("industry-2");
        entity.setAccountManager("accountManager-2");
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("CUSTOM-0002");
        entity.setName("Customer two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CustomerResponse response() {
        CustomerResponse response = new CustomerResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CUSTOM-0001");
        response.setName("Customer one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CustomerSearchCriteria criteria() {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
