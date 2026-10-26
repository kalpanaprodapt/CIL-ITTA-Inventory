package com.uniti.cil.itta.inventory.assetmgmt.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for AssetTag. */
public final class AssetTagFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private AssetTagFixtures() {
    }

    public static AssetTagCreateRequest createRequest() {
        AssetTagCreateRequest request = new AssetTagCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000011"));
        request.setTagValue("tagValue-1");
        request.setTagType(AssetTagTagType.BARCODE);
        request.setPrintedOn(LocalDate.of(2026, 2, 11));
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(AssetTagCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setCode("ASSETT-0001");
        request.setName("Asset Tag one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static AssetTag entity() {
        AssetTag entity = new AssetTag();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000012"));
        entity.setTagValue("tagValue-2");
        entity.setTagType(AssetTagTagType.RFID);
        entity.setPrintedOn(LocalDate.of(2026, 3, 12));
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(AssetTagCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setCode("ASSETT-0002");
        entity.setName("Asset Tag two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static AssetTagResponse response() {
        AssetTagResponse response = new AssetTagResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("ASSETT-0001");
        response.setName("Asset Tag one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static AssetTagSearchCriteria criteria() {
        AssetTagSearchCriteria criteria = new AssetTagSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
