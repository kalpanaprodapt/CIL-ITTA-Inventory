package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import com.uniti.cil.itta.inventory.assetmgmt.support.AssetFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AssetMapperTest {

    private final AssetMapper mapper = new AssetMapper();

    @Test
    void toEntityCopiesEveryField() {
        AssetCreateRequest request = AssetFixtures.createRequest();
        Asset entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAssetTag()).isEqualTo(request.getAssetTag());
        assertThat(entity.getAssetClass()).isEqualTo(request.getAssetClass());
        assertThat(entity.getAcquiredOn()).isEqualTo(request.getAcquiredOn());
        assertThat(entity.getPurchasePrice()).isEqualTo(request.getPurchasePrice());
        assertThat(entity.getCurrentValue()).isEqualTo(request.getCurrentValue());
        assertThat(entity.getCustodian()).isEqualTo(request.getCustodian());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        Asset entity = AssetFixtures.entity();
        AssetResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAssetTag()).isEqualTo(entity.getAssetTag());
        assertThat(response.getAssetClass()).isEqualTo(entity.getAssetClass());
        assertThat(response.getAcquiredOn()).isEqualTo(entity.getAcquiredOn());
        assertThat(response.getPurchasePrice()).isEqualTo(entity.getPurchasePrice());
        assertThat(response.getCurrentValue()).isEqualTo(entity.getCurrentValue());
        assertThat(response.getCustodian()).isEqualTo(entity.getCustodian());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        Asset entity = AssetFixtures.entity();
        AssetSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(AssetFixtures.entity())).hasSameSizeAs(AssetMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Asset entity = AssetFixtures.entity();
        AssetUpdateRequest request = new AssetUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAssetTag("assetTag-3");
        request.setAssetClass("assetClass-3");
        request.setAcquiredOn(LocalDate.of(2026, 4, 13));
        request.setPurchasePrice(new BigDecimal("15.50"));
        request.setCurrentValue(new BigDecimal("15.50"));
        request.setCustodian("custodian-3");
        request.setLabels("labels-3");
        request.setLifecycleStage(AssetLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getAssetTag()).isEqualTo("assetTag-3");
        assertThat(entity.getAssetClass()).isEqualTo("assetClass-3");
        assertThat(entity.getAcquiredOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getPurchasePrice()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCurrentValue()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCustodian()).isEqualTo("custodian-3");
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(AssetLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Asset entity = AssetFixtures.entity();
        Asset before = AssetFixtures.entity();
        mapper.applyUpdate(new AssetUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
