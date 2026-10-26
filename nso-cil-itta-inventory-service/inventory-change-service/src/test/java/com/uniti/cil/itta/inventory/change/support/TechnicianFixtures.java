package com.uniti.cil.itta.inventory.change.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Technician. */
public final class TechnicianFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private TechnicianFixtures() {
    }

    public static TechnicianCreateRequest createRequest() {
        TechnicianCreateRequest request = new TechnicianCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setEmployeeId("employeeId-1");
        request.setFullName("fullName-1");
        request.setSkillSet("skillSet-1");
        request.setHomeSiteId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setCertificationLevel(11);
        request.setCriticality(TechnicianCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("TECHNI-0001");
        request.setName("Technician one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Technician entity() {
        Technician entity = new Technician();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setEmployeeId("employeeId-2");
        entity.setFullName("fullName-2");
        entity.setSkillSet("skillSet-2");
        entity.setHomeSiteId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setCertificationLevel(12);
        entity.setCriticality(TechnicianCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("TECHNI-0002");
        entity.setName("Technician two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static TechnicianResponse response() {
        TechnicianResponse response = new TechnicianResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("TECHNI-0001");
        response.setName("Technician one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static TechnicianSearchCriteria criteria() {
        TechnicianSearchCriteria criteria = new TechnicianSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
