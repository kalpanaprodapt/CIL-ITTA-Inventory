package com.uniti.cil.itta.inventory.software.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for SoftwareVersion. */
public final class SoftwareVersionFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SoftwareVersionFixtures() {
    }

    public static SoftwareVersionCreateRequest createRequest() {
        SoftwareVersionCreateRequest request = new SoftwareVersionCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setImageId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setVersionString("versionString-1");
        request.setReleaseDate(LocalDate.of(2026, 2, 11));
        request.setEndOfSupport(LocalDate.of(2026, 2, 11));
        request.setReleaseTrain(SoftwareVersionReleaseTrain.GA);
        request.setCveCount(11);
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(SoftwareVersionCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("SOFTWA-0001");
        request.setName("Software Version one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static SoftwareVersion entity() {
        SoftwareVersion entity = new SoftwareVersion();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setImageId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setVersionString("versionString-2");
        entity.setReleaseDate(LocalDate.of(2026, 3, 12));
        entity.setEndOfSupport(LocalDate.of(2026, 3, 12));
        entity.setReleaseTrain(SoftwareVersionReleaseTrain.MAINTENANCE);
        entity.setCveCount(12);
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(SoftwareVersionCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("SOFTWA-0002");
        entity.setName("Software Version two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SoftwareVersionResponse response() {
        SoftwareVersionResponse response = new SoftwareVersionResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SOFTWA-0001");
        response.setName("Software Version one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SoftwareVersionSearchCriteria criteria() {
        SoftwareVersionSearchCriteria criteria = new SoftwareVersionSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
