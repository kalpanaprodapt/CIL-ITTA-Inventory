package com.uniti.cil.itta.inventory.software.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for SoftwareImage. */
public final class SoftwareImageFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private SoftwareImageFixtures() {
    }

    public static SoftwareImageCreateRequest createRequest() {
        SoftwareImageCreateRequest request = new SoftwareImageCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setImageName("imageName-1");
        request.setSizeMb(1001L);
        request.setChecksum("checksum-1");
        request.setPlatform("platform-1");
        request.setStoragePath("storagePath-1");
        request.setLifecycleStage(SoftwareImageLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(SoftwareImageCriticality.LOW);
        request.setCode("SOFTWA-0001");
        request.setName("Software Image one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static SoftwareImage entity() {
        SoftwareImage entity = new SoftwareImage();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setImageName("imageName-2");
        entity.setSizeMb(1002L);
        entity.setChecksum("checksum-2");
        entity.setPlatform("platform-2");
        entity.setStoragePath("storagePath-2");
        entity.setLifecycleStage(SoftwareImageLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(SoftwareImageCriticality.MEDIUM);
        entity.setCode("SOFTWA-0002");
        entity.setName("Software Image two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static SoftwareImageResponse response() {
        SoftwareImageResponse response = new SoftwareImageResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("SOFTWA-0001");
        response.setName("Software Image one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static SoftwareImageSearchCriteria criteria() {
        SoftwareImageSearchCriteria criteria = new SoftwareImageSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
