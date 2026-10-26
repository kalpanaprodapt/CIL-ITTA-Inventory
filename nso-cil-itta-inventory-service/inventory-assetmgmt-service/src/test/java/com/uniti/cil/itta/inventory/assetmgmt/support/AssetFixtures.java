package com.uniti.cil.itta.inventory.assetmgmt.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Asset. */
public final class AssetFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private AssetFixtures() {
    }

    public static AssetCreateRequest createRequest() {
        AssetCreateRequest request = new AssetCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAssetTag("assetTag-1");
        request.setAssetClass("assetClass-1");
        request.setAcquiredOn(LocalDate.of(2026, 2, 11));
        request.setPurchasePrice(new BigDecimal("13.50"));
        request.setCurrentValue(new BigDecimal("13.50"));
        request.setCustodian("custodian-1");
        request.setLabels("labels-1");
        request.setLifecycleStage(AssetLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("ASSET-0001");
        request.setName("Asset one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Asset entity() {
        Asset entity = new Asset();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAssetTag("assetTag-2");
        entity.setAssetClass("assetClass-2");
        entity.setAcquiredOn(LocalDate.of(2026, 3, 12));
        entity.setPurchasePrice(new BigDecimal("14.50"));
        entity.setCurrentValue(new BigDecimal("14.50"));
        entity.setCustodian("custodian-2");
        entity.setLabels("labels-2");
        entity.setLifecycleStage(AssetLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("ASSET-0002");
        entity.setName("Asset two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static AssetResponse response() {
        AssetResponse response = new AssetResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("ASSET-0001");
        response.setName("Asset one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static AssetSearchCriteria criteria() {
        AssetSearchCriteria criteria = new AssetSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
