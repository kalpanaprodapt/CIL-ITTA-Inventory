package com.uniti.cil.itta.inventory.mpls.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for VpnService. */
public final class VpnServiceFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private VpnServiceFixtures() {
    }

    public static VpnServiceCreateRequest createRequest() {
        VpnServiceCreateRequest request = new VpnServiceCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setVpnName("vpnName-1");
        request.setVpnType(VpnServiceVpnType.L3VPN);
        request.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTopology(VpnServiceTopology.MESH);
        request.setOwner("owner-1");
        request.setCriticality(VpnServiceCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setCode("VPNSER-0001");
        request.setName("Vpn Service one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static VpnService entity() {
        VpnService entity = new VpnService();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setVpnName("vpnName-2");
        entity.setVpnType(VpnServiceVpnType.L2VPN);
        entity.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTopology(VpnServiceTopology.HUB_SPOKE);
        entity.setOwner("owner-2");
        entity.setCriticality(VpnServiceCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setCode("VPNSER-0002");
        entity.setName("Vpn Service two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static VpnServiceResponse response() {
        VpnServiceResponse response = new VpnServiceResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("VPNSER-0001");
        response.setName("Vpn Service one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static VpnServiceSearchCriteria criteria() {
        VpnServiceSearchCriteria criteria = new VpnServiceSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
