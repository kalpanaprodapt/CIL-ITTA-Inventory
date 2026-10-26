package com.uniti.cil.itta.inventory.ipam.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for IpPool. */
public final class IpPoolFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private IpPoolFixtures() {
    }

    public static IpPoolCreateRequest createRequest() {
        IpPoolCreateRequest request = new IpPoolCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setSubnetId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setRangeStart("rangeStart-1");
        request.setRangeEnd("rangeEnd-1");
        request.setPoolType(IpPoolPoolType.DYNAMIC);
        request.setLeaseSeconds(11);
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(IpPoolLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("IPPOOL-0001");
        request.setName("Ip Pool one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static IpPool entity() {
        IpPool entity = new IpPool();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setSubnetId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setRangeStart("rangeStart-2");
        entity.setRangeEnd("rangeEnd-2");
        entity.setPoolType(IpPoolPoolType.STATIC);
        entity.setLeaseSeconds(12);
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(IpPoolLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("IPPOOL-0002");
        entity.setName("Ip Pool two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static IpPoolResponse response() {
        IpPoolResponse response = new IpPoolResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("IPPOOL-0001");
        response.setName("Ip Pool one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static IpPoolSearchCriteria criteria() {
        IpPoolSearchCriteria criteria = new IpPoolSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
