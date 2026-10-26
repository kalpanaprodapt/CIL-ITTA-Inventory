package com.uniti.cil.itta.inventory.vendor.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Vendor. */
public final class VendorFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private VendorFixtures() {
    }

    public static VendorCreateRequest createRequest() {
        VendorCreateRequest request = new VendorCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setLegalName("legalName-1");
        request.setSupportEmail("supportEmail-1");
        request.setSupportPhone("supportPhone-1");
        request.setWebsite("website-1");
        request.setTier(VendorTier.STRATEGIC);
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(VendorLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("VENDOR-0001");
        request.setName("Vendor one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Vendor entity() {
        Vendor entity = new Vendor();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setLegalName("legalName-2");
        entity.setSupportEmail("supportEmail-2");
        entity.setSupportPhone("supportPhone-2");
        entity.setWebsite("website-2");
        entity.setTier(VendorTier.PREFERRED);
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(VendorLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("VENDOR-0002");
        entity.setName("Vendor two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static VendorResponse response() {
        VendorResponse response = new VendorResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("VENDOR-0001");
        response.setName("Vendor one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static VendorSearchCriteria criteria() {
        VendorSearchCriteria criteria = new VendorSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
