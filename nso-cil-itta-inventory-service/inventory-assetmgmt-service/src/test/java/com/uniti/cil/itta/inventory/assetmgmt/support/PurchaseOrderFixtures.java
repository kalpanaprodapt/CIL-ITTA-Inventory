package com.uniti.cil.itta.inventory.assetmgmt.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for PurchaseOrder. */
public final class PurchaseOrderFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PurchaseOrderFixtures() {
    }

    public static PurchaseOrderCreateRequest createRequest() {
        PurchaseOrderCreateRequest request = new PurchaseOrderCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setPoNumber("poNumber-1");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setOrderedOn(LocalDate.of(2026, 2, 11));
        request.setTotalAmount(new BigDecimal("13.50"));
        request.setCurrency("currency-1");
        request.setOwner("owner-1");
        request.setCriticality(PurchaseOrderCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("PURCHA-0001");
        request.setName("Purchase Order one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static PurchaseOrder entity() {
        PurchaseOrder entity = new PurchaseOrder();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setPoNumber("poNumber-2");
        entity.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setOrderedOn(LocalDate.of(2026, 3, 12));
        entity.setTotalAmount(new BigDecimal("14.50"));
        entity.setCurrency("currency-2");
        entity.setOwner("owner-2");
        entity.setCriticality(PurchaseOrderCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("PURCHA-0002");
        entity.setName("Purchase Order two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PurchaseOrderResponse response() {
        PurchaseOrderResponse response = new PurchaseOrderResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("PURCHA-0001");
        response.setName("Purchase Order one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PurchaseOrderSearchCriteria criteria() {
        PurchaseOrderSearchCriteria criteria = new PurchaseOrderSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
