package com.uniti.cil.itta.inventory.customer.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for CustomerContact. */
public final class CustomerContactFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CustomerContactFixtures() {
    }

    public static CustomerContactCreateRequest createRequest() {
        CustomerContactCreateRequest request = new CustomerContactCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setFullName("fullName-1");
        request.setEmail("email-1");
        request.setPhone("phone-1");
        request.setContactRole(CustomerContactContactRole.TECHNICAL);
        request.setLabels("labels-1");
        request.setLifecycleStage(CustomerContactLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("CUSTOM-0001");
        request.setName("Customer Contact one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static CustomerContact entity() {
        CustomerContact entity = new CustomerContact();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setFullName("fullName-2");
        entity.setEmail("email-2");
        entity.setPhone("phone-2");
        entity.setContactRole(CustomerContactContactRole.BILLING);
        entity.setLabels("labels-2");
        entity.setLifecycleStage(CustomerContactLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("CUSTOM-0002");
        entity.setName("Customer Contact two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CustomerContactResponse response() {
        CustomerContactResponse response = new CustomerContactResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CUSTOM-0001");
        response.setName("Customer Contact one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CustomerContactSearchCriteria criteria() {
        CustomerContactSearchCriteria criteria = new CustomerContactSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
