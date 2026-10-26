package com.uniti.cil.itta.inventory.ipam.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for IpSubnet. */
public final class IpSubnetFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private IpSubnetFixtures() {
    }

    public static IpSubnetCreateRequest createRequest() {
        IpSubnetCreateRequest request = new IpSubnetCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCidr("cidr-1");
        request.setIpVersion(IpSubnetIpVersion.V4);
        request.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setGateway("gateway-1");
        request.setVlanId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setUtilizationPct(new BigDecimal("13.50"));
        request.setOwner("owner-1");
        request.setCriticality(IpSubnetCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("IPSUBN-0001");
        request.setName("Ip Subnet one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static IpSubnet entity() {
        IpSubnet entity = new IpSubnet();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCidr("cidr-2");
        entity.setIpVersion(IpSubnetIpVersion.V6);
        entity.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setGateway("gateway-2");
        entity.setVlanId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setUtilizationPct(new BigDecimal("14.50"));
        entity.setOwner("owner-2");
        entity.setCriticality(IpSubnetCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("IPSUBN-0002");
        entity.setName("Ip Subnet two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static IpSubnetResponse response() {
        IpSubnetResponse response = new IpSubnetResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("IPSUBN-0001");
        response.setName("Ip Subnet one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static IpSubnetSearchCriteria criteria() {
        IpSubnetSearchCriteria criteria = new IpSubnetSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
