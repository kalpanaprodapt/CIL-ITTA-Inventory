package com.uniti.cil.itta.inventory.cabling.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Cable. */
public final class CableFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CableFixtures() {
    }

    public static CableCreateRequest createRequest() {
        CableCreateRequest request = new CableCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCableType(CableCableType.CAT6);
        request.setLengthM(new BigDecimal("13.50"));
        request.setAEndPortId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setZEndPortId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setColor("color-1");
        request.setLabel("label-1");
        request.setCriticality(CableCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("CABLE-0001");
        request.setName("Cable one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Cable entity() {
        Cable entity = new Cable();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCableType(CableCableType.SM_FIBER);
        entity.setLengthM(new BigDecimal("14.50"));
        entity.setAEndPortId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setZEndPortId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setColor("color-2");
        entity.setLabel("label-2");
        entity.setCriticality(CableCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("CABLE-0002");
        entity.setName("Cable two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CableResponse response() {
        CableResponse response = new CableResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CABLE-0001");
        response.setName("Cable one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CableSearchCriteria criteria() {
        CableSearchCriteria criteria = new CableSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
