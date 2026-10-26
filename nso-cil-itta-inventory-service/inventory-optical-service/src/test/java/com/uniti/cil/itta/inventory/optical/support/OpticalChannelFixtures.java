package com.uniti.cil.itta.inventory.optical.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for OpticalChannel. */
public final class OpticalChannelFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private OpticalChannelFixtures() {
    }

    public static OpticalChannelCreateRequest createRequest() {
        OpticalChannelCreateRequest request = new OpticalChannelCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setWavelengthNm(new BigDecimal("13.50"));
        request.setChannelNumber(11);
        request.setGridType(OpticalChannelGridType.FIXED);
        request.setBitrateGbps(new BigDecimal("13.50"));
        request.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setCriticality(OpticalChannelCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("OPTICA-0001");
        request.setName("Optical Channel one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static OpticalChannel entity() {
        OpticalChannel entity = new OpticalChannel();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setWavelengthNm(new BigDecimal("14.50"));
        entity.setChannelNumber(12);
        entity.setGridType(OpticalChannelGridType.FLEX);
        entity.setBitrateGbps(new BigDecimal("14.50"));
        entity.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setCriticality(OpticalChannelCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("OPTICA-0002");
        entity.setName("Optical Channel two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static OpticalChannelResponse response() {
        OpticalChannelResponse response = new OpticalChannelResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("OPTICA-0001");
        response.setName("Optical Channel one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static OpticalChannelSearchCriteria criteria() {
        OpticalChannelSearchCriteria criteria = new OpticalChannelSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
