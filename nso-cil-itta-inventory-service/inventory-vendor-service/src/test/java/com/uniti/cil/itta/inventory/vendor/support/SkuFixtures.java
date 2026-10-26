package com.uniti.cil.itta.inventory.vendor.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Sku. */
public final class SkuFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SkuFixtures() {
    }

    public static SkuCreateRequest createRequest() {
        SkuCreateRequest request = new SkuCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setModelId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setSkuCode("skuCode-1");
        request.setListPrice(new BigDecimal("13.50"));
        request.setCurrency("currency-1");
        request.setLeadTimeDays(11);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(SkuCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("SKU-0001");
        request.setName("Sku one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Sku entity() {
        Sku entity = new Sku();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setModelId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setSkuCode("skuCode-2");
        entity.setListPrice(new BigDecimal("14.50"));
        entity.setCurrency("currency-2");
        entity.setLeadTimeDays(12);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(SkuCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("SKU-0002");
        entity.setName("Sku two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SkuResponse response() {
        SkuResponse response = new SkuResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SKU-0001");
        response.setName("Sku one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SkuSearchCriteria criteria() {
        SkuSearchCriteria criteria = new SkuSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
