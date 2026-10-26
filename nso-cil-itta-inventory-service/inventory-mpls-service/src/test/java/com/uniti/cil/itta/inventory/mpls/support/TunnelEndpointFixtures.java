package com.uniti.cil.itta.inventory.mpls.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for TunnelEndpoint. */
public final class TunnelEndpointFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private TunnelEndpointFixtures() {
    }

    public static TunnelEndpointCreateRequest createRequest() {
        TunnelEndpointCreateRequest request = new TunnelEndpointCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTunnelName("tunnelName-1");
        request.setTunnelType(TunnelEndpointTunnelType.GRE);
        request.setSourceIp("sourceIp-1");
        request.setDestinationIp("destinationIp-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(TunnelEndpointLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("TUNNEL-0001");
        request.setName("Tunnel Endpoint one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static TunnelEndpoint entity() {
        TunnelEndpoint entity = new TunnelEndpoint();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTunnelName("tunnelName-2");
        entity.setTunnelType(TunnelEndpointTunnelType.IPSEC);
        entity.setSourceIp("sourceIp-2");
        entity.setDestinationIp("destinationIp-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(TunnelEndpointLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("TUNNEL-0002");
        entity.setName("Tunnel Endpoint two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static TunnelEndpointResponse response() {
        TunnelEndpointResponse response = new TunnelEndpointResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("TUNNEL-0001");
        response.setName("Tunnel Endpoint one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static TunnelEndpointSearchCriteria criteria() {
        TunnelEndpointSearchCriteria criteria = new TunnelEndpointSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
