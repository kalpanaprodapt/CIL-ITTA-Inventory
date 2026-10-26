package com.uniti.cil.itta.inventory.mpls.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for MplsLsp. */
public final class MplsLspFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private MplsLspFixtures() {
    }

    public static MplsLspCreateRequest createRequest() {
        MplsLspCreateRequest request = new MplsLspCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setLspName("lspName-1");
        request.setHeadEndId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTailEndId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setBandwidthMbps(1001L);
        request.setSetupPriority(11);
        request.setPathType(MplsLspPathType.PRIMARY);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(MplsLspCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("MPLSLS-0001");
        request.setName("Mpls Lsp one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static MplsLsp entity() {
        MplsLsp entity = new MplsLsp();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setLspName("lspName-2");
        entity.setHeadEndId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTailEndId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setBandwidthMbps(1002L);
        entity.setSetupPriority(12);
        entity.setPathType(MplsLspPathType.SECONDARY);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(MplsLspCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("MPLSLS-0002");
        entity.setName("Mpls Lsp two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static MplsLspResponse response() {
        MplsLspResponse response = new MplsLspResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("MPLSLS-0001");
        response.setName("Mpls Lsp one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static MplsLspSearchCriteria criteria() {
        MplsLspSearchCriteria criteria = new MplsLspSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
