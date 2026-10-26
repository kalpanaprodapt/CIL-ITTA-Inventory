package com.uniti.cil.itta.inventory.switching.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for BgpSession. */
public final class BgpSessionFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private BgpSessionFixtures() {
    }

    public static BgpSessionCreateRequest createRequest() {
        BgpSessionCreateRequest request = new BgpSessionCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setLocalDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setPeerAddress("peerAddress-1");
        request.setLocalAsn(1001L);
        request.setRemoteAsn(1001L);
        request.setSessionState(BgpSessionSessionState.IDLE);
        request.setPrefixLimit(11);
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(BgpSessionCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("BGPSES-0001");
        request.setName("Bgp Session one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static BgpSession entity() {
        BgpSession entity = new BgpSession();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setLocalDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setPeerAddress("peerAddress-2");
        entity.setLocalAsn(1002L);
        entity.setRemoteAsn(1002L);
        entity.setSessionState(BgpSessionSessionState.CONNECT);
        entity.setPrefixLimit(12);
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(BgpSessionCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("BGPSES-0002");
        entity.setName("Bgp Session two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static BgpSessionResponse response() {
        BgpSessionResponse response = new BgpSessionResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("BGPSES-0001");
        response.setName("Bgp Session one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static BgpSessionSearchCriteria criteria() {
        BgpSessionSearchCriteria criteria = new BgpSessionSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
