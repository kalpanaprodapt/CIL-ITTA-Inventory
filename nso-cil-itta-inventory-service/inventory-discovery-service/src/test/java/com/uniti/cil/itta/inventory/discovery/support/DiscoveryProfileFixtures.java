package com.uniti.cil.itta.inventory.discovery.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for DiscoveryProfile. */
public final class DiscoveryProfileFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private DiscoveryProfileFixtures() {
    }

    public static DiscoveryProfileCreateRequest createRequest() {
        DiscoveryProfileCreateRequest request = new DiscoveryProfileCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setProfileName("profileName-1");
        request.setProtocol(DiscoveryProfileProtocol.SNMP);
        request.setCredentialRef("credentialRef-1");
        request.setScopeCidr("scopeCidr-1");
        request.setScheduleCron("scheduleCron-1");
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setCode("DISCOV-0001");
        request.setName("Discovery Profile one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static DiscoveryProfile entity() {
        DiscoveryProfile entity = new DiscoveryProfile();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setProfileName("profileName-2");
        entity.setProtocol(DiscoveryProfileProtocol.SSH);
        entity.setCredentialRef("credentialRef-2");
        entity.setScopeCidr("scopeCidr-2");
        entity.setScheduleCron("scheduleCron-2");
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setCode("DISCOV-0002");
        entity.setName("Discovery Profile two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static DiscoveryProfileResponse response() {
        DiscoveryProfileResponse response = new DiscoveryProfileResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("DISCOV-0001");
        response.setName("Discovery Profile one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static DiscoveryProfileSearchCriteria criteria() {
        DiscoveryProfileSearchCriteria criteria = new DiscoveryProfileSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
