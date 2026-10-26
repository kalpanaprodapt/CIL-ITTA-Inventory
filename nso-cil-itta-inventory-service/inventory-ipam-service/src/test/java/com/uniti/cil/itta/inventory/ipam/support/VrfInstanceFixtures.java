package com.uniti.cil.itta.inventory.ipam.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for VrfInstance. */
public final class VrfInstanceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private VrfInstanceFixtures() {
    }

    public static VrfInstanceCreateRequest createRequest() {
        VrfInstanceCreateRequest request = new VrfInstanceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVrfName("vrfName-1");
        request.setRouteDistinguisher("routeDistinguisher-1");
        request.setImportTargets("importTargets-1");
        request.setExportTargets("exportTargets-1");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLabels("labels-1");
        request.setLifecycleStage(VrfInstanceLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("VRFINS-0001");
        request.setName("Vrf Instance one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static VrfInstance entity() {
        VrfInstance entity = new VrfInstance();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVrfName("vrfName-2");
        entity.setRouteDistinguisher("routeDistinguisher-2");
        entity.setImportTargets("importTargets-2");
        entity.setExportTargets("exportTargets-2");
        entity.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(VrfInstanceLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("VRFINS-0002");
        entity.setName("Vrf Instance two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static VrfInstanceResponse response() {
        VrfInstanceResponse response = new VrfInstanceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("VRFINS-0001");
        response.setName("Vrf Instance one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static VrfInstanceSearchCriteria criteria() {
        VrfInstanceSearchCriteria criteria = new VrfInstanceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
