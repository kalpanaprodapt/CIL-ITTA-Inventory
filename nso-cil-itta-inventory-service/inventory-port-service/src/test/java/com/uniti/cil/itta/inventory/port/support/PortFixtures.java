package com.uniti.cil.itta.inventory.port.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Port. */
public final class PortFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private PortFixtures() {
    }

    public static PortCreateRequest createRequest() {
        PortCreateRequest request = new PortCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setCardId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPortNumber("portNumber-1");
        request.setSpeedMbps(1001L);
        request.setMediaType(PortMediaType.COPPER);
        request.setAdminState(PortAdminState.UP);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(PortCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("PORT-0001");
        request.setName("Port one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Port entity() {
        Port entity = new Port();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setCardId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPortNumber("portNumber-2");
        entity.setSpeedMbps(1002L);
        entity.setMediaType(PortMediaType.FIBER);
        entity.setAdminState(PortAdminState.DOWN);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(PortCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("PORT-0002");
        entity.setName("Port two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static PortResponse response() {
        PortResponse response = new PortResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("PORT-0001");
        response.setName("Port one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static PortSearchCriteria criteria() {
        PortSearchCriteria criteria = new PortSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
