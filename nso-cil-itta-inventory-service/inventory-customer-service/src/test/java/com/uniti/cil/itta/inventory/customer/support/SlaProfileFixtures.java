package com.uniti.cil.itta.inventory.customer.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for SlaProfile. */
public final class SlaProfileFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SlaProfileFixtures() {
    }

    public static SlaProfileCreateRequest createRequest() {
        SlaProfileCreateRequest request = new SlaProfileCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setProfileName("profileName-1");
        request.setAvailabilityPct(new BigDecimal("13.50"));
        request.setMttrMinutes(11);
        request.setLatencyMs(new BigDecimal("13.50"));
        request.setJitterMs(new BigDecimal("13.50"));
        request.setCreditPolicy("creditPolicy-1");
        request.setOwner("owner-1");
        request.setCriticality(SlaProfileCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("SLAPRO-0001");
        request.setName("Sla Profile one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static SlaProfile entity() {
        SlaProfile entity = new SlaProfile();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setProfileName("profileName-2");
        entity.setAvailabilityPct(new BigDecimal("14.50"));
        entity.setMttrMinutes(12);
        entity.setLatencyMs(new BigDecimal("14.50"));
        entity.setJitterMs(new BigDecimal("14.50"));
        entity.setCreditPolicy("creditPolicy-2");
        entity.setOwner("owner-2");
        entity.setCriticality(SlaProfileCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("SLAPRO-0002");
        entity.setName("Sla Profile two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SlaProfileResponse response() {
        SlaProfileResponse response = new SlaProfileResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SLAPRO-0001");
        response.setName("Sla Profile one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SlaProfileSearchCriteria criteria() {
        SlaProfileSearchCriteria criteria = new SlaProfileSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
