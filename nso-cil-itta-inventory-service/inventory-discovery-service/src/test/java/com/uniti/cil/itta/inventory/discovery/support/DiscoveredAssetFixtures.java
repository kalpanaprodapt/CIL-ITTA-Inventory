package com.uniti.cil.itta.inventory.discovery.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for DiscoveredAsset. */
public final class DiscoveredAssetFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private DiscoveredAssetFixtures() {
    }

    public static DiscoveredAssetCreateRequest createRequest() {
        DiscoveredAssetCreateRequest request = new DiscoveredAssetCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setAssetType("assetType-1");
        request.setFingerprint("fingerprint-1");
        request.setRawPayload("rawPayload-1");
        request.setMatchedAssetId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(DiscoveredAssetLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setCode("DISCOV-0001");
        request.setName("Discovered Asset one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static DiscoveredAsset entity() {
        DiscoveredAsset entity = new DiscoveredAsset();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setAssetType("assetType-2");
        entity.setFingerprint("fingerprint-2");
        entity.setRawPayload("rawPayload-2");
        entity.setMatchedAssetId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(DiscoveredAssetLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setCode("DISCOV-0002");
        entity.setName("Discovered Asset two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static DiscoveredAssetResponse response() {
        DiscoveredAssetResponse response = new DiscoveredAssetResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("DISCOV-0001");
        response.setName("Discovered Asset one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static DiscoveredAssetSearchCriteria criteria() {
        DiscoveredAssetSearchCriteria criteria = new DiscoveredAssetSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
