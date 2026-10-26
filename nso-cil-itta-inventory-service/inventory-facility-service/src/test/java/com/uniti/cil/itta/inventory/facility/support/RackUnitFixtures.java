package com.uniti.cil.itta.inventory.facility.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for RackUnit. */
public final class RackUnitFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private RackUnitFixtures() {
    }

    public static RackUnitCreateRequest createRequest() {
        RackUnitCreateRequest request = new RackUnitCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setRackId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setUnitNumber(11);
        request.setOccupied(Boolean.TRUE);
        request.setMountedDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setFace(RackUnitFace.FRONT);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(RackUnitCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("RACKUN-0001");
        request.setName("Rack Unit one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static RackUnit entity() {
        RackUnit entity = new RackUnit();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setRackId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setUnitNumber(12);
        entity.setOccupied(Boolean.FALSE);
        entity.setMountedDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setFace(RackUnitFace.REAR);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(RackUnitCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("RACKUN-0002");
        entity.setName("Rack Unit two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static RackUnitResponse response() {
        RackUnitResponse response = new RackUnitResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("RACKUN-0001");
        response.setName("Rack Unit one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static RackUnitSearchCriteria criteria() {
        RackUnitSearchCriteria criteria = new RackUnitSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
