package com.uniti.cil.itta.inventory.service.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ServiceCatalogItem. */
public final class ServiceCatalogItemFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ServiceCatalogItemFixtures() {
    }

    public static ServiceCatalogItemCreateRequest createRequest() {
        ServiceCatalogItemCreateRequest request = new ServiceCatalogItemCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setItemName("itemName-1");
        request.setFamily(ServiceCatalogItemFamily.CONNECTIVITY);
        request.setBasePrice(new BigDecimal("13.50"));
        request.setBillingUnit("billingUnit-1");
        request.setOrderable(Boolean.TRUE);
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(ServiceCatalogItemLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("SERVIC-0001");
        request.setName("Service Catalog Item one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ServiceCatalogItem entity() {
        ServiceCatalogItem entity = new ServiceCatalogItem();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setItemName("itemName-2");
        entity.setFamily(ServiceCatalogItemFamily.SECURITY);
        entity.setBasePrice(new BigDecimal("14.50"));
        entity.setBillingUnit("billingUnit-2");
        entity.setOrderable(Boolean.FALSE);
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(ServiceCatalogItemLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("SERVIC-0002");
        entity.setName("Service Catalog Item two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ServiceCatalogItemResponse response() {
        ServiceCatalogItemResponse response = new ServiceCatalogItemResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SERVIC-0001");
        response.setName("Service Catalog Item one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ServiceCatalogItemSearchCriteria criteria() {
        ServiceCatalogItemSearchCriteria criteria = new ServiceCatalogItemSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
