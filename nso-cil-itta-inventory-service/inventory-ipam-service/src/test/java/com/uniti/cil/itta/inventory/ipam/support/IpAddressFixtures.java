package com.uniti.cil.itta.inventory.ipam.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for IpAddress. */
public final class IpAddressFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private IpAddressFixtures() {
    }

    public static IpAddressCreateRequest createRequest() {
        IpAddressCreateRequest request = new IpAddressCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSubnetId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setAddress("address-1");
        request.setAssignedToId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setAssignmentType(IpAddressAssignmentType.STATIC);
        request.setDnsName("dnsName-1");
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("IPADDR-0001");
        request.setName("Ip Address one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static IpAddress entity() {
        IpAddress entity = new IpAddress();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSubnetId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setAddress("address-2");
        entity.setAssignedToId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setAssignmentType(IpAddressAssignmentType.DHCP);
        entity.setDnsName("dnsName-2");
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("IPADDR-0002");
        entity.setName("Ip Address two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static IpAddressResponse response() {
        IpAddressResponse response = new IpAddressResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("IPADDR-0001");
        response.setName("Ip Address one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static IpAddressSearchCriteria criteria() {
        IpAddressSearchCriteria criteria = new IpAddressSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
