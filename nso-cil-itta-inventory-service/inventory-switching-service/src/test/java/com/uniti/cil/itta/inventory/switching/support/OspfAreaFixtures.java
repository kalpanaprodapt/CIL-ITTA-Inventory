package com.uniti.cil.itta.inventory.switching.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for OspfArea. */
public final class OspfAreaFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private OspfAreaFixtures() {
    }

    public static OspfAreaCreateRequest createRequest() {
        OspfAreaCreateRequest request = new OspfAreaCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAreaId("areaId-1");
        request.setAreaType(OspfAreaAreaType.BACKBONE);
        request.setDeviceCount(11);
        request.setAuthType("authType-1");
        request.setCriticality(OspfAreaCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("OSPFAR-0001");
        request.setName("Ospf Area one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static OspfArea entity() {
        OspfArea entity = new OspfArea();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAreaId("areaId-2");
        entity.setAreaType(OspfAreaAreaType.STANDARD);
        entity.setDeviceCount(12);
        entity.setAuthType("authType-2");
        entity.setCriticality(OspfAreaCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("OSPFAR-0002");
        entity.setName("Ospf Area two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static OspfAreaResponse response() {
        OspfAreaResponse response = new OspfAreaResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("OSPFAR-0001");
        response.setName("Ospf Area one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static OspfAreaSearchCriteria criteria() {
        OspfAreaSearchCriteria criteria = new OspfAreaSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
