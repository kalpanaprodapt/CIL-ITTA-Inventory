package com.uniti.cil.itta.inventory.topology.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Link. */
public final class LinkFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private LinkFixtures() {
    }

    public static LinkCreateRequest createRequest() {
        LinkCreateRequest request = new LinkCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setZInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setLinkType(LinkLinkType.PHYSICAL);
        request.setSpeedMbps(1001L);
        request.setDiscoveredBy("discoveredBy-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(LinkCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("LINK-0001");
        request.setName("Link one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Link entity() {
        Link entity = new Link();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setZInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setLinkType(LinkLinkType.LOGICAL);
        entity.setSpeedMbps(1002L);
        entity.setDiscoveredBy("discoveredBy-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(LinkCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("LINK-0002");
        entity.setName("Link two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static LinkResponse response() {
        LinkResponse response = new LinkResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("LINK-0001");
        response.setName("Link one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static LinkSearchCriteria criteria() {
        LinkSearchCriteria criteria = new LinkSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
