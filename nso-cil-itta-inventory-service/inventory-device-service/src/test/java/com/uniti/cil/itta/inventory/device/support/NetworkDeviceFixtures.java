package com.uniti.cil.itta.inventory.device.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for NetworkDevice. */
public final class NetworkDeviceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private NetworkDeviceFixtures() {
    }

    public static NetworkDeviceCreateRequest createRequest() {
        NetworkDeviceCreateRequest request = new NetworkDeviceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setHostname("hostname-1");
        request.setDeviceRole(NetworkDeviceDeviceRole.CORE);
        request.setMgmtIp("mgmtIp-1");
        request.setSerialNumber("serialNumber-1");
        request.setModelId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setRackUnitId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(NetworkDeviceLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setCode("NETWOR-0001");
        request.setName("Network Device one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static NetworkDevice entity() {
        NetworkDevice entity = new NetworkDevice();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setHostname("hostname-2");
        entity.setDeviceRole(NetworkDeviceDeviceRole.EDGE);
        entity.setMgmtIp("mgmtIp-2");
        entity.setSerialNumber("serialNumber-2");
        entity.setModelId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setRackUnitId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(NetworkDeviceLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setCode("NETWOR-0002");
        entity.setName("Network Device two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static NetworkDeviceResponse response() {
        NetworkDeviceResponse response = new NetworkDeviceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("NETWOR-0001");
        response.setName("Network Device one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static NetworkDeviceSearchCriteria criteria() {
        NetworkDeviceSearchCriteria criteria = new NetworkDeviceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
