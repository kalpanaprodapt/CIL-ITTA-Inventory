package com.uniti.cil.itta.inventory.vendor.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Warranty. */
public final class WarrantyFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private WarrantyFixtures() {
    }

    public static WarrantyCreateRequest createRequest() {
        WarrantyCreateRequest request = new WarrantyCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setStartDate(LocalDate.of(2026, 2, 11));
        request.setEndDate(LocalDate.of(2026, 2, 11));
        request.setWarrantyType(WarrantyWarrantyType.STANDARD);
        request.setTerms("terms-1");
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("WARRAN-0001");
        request.setName("Warranty one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Warranty entity() {
        Warranty entity = new Warranty();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setStartDate(LocalDate.of(2026, 3, 12));
        entity.setEndDate(LocalDate.of(2026, 3, 12));
        entity.setWarrantyType(WarrantyWarrantyType.EXTENDED);
        entity.setTerms("terms-2");
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("WARRAN-0002");
        entity.setName("Warranty two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static WarrantyResponse response() {
        WarrantyResponse response = new WarrantyResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("WARRAN-0001");
        response.setName("Warranty one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static WarrantySearchCriteria criteria() {
        WarrantySearchCriteria criteria = new WarrantySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
