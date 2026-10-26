package com.uniti.cil.itta.inventory.discovery.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveredAssetFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscoveredAssetMapperTest {

    private final DiscoveredAssetMapper mapper = new DiscoveredAssetMapper();

    @Test
    void toEntityCopiesEveryField() {
        DiscoveredAssetCreateRequest request = DiscoveredAssetFixtures.createRequest();
        DiscoveredAsset entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getJobId()).isEqualTo(request.getJobId());
        assertThat(entity.getAssetType()).isEqualTo(request.getAssetType());
        assertThat(entity.getFingerprint()).isEqualTo(request.getFingerprint());
        assertThat(entity.getRawPayload()).isEqualTo(request.getRawPayload());
        assertThat(entity.getMatchedAssetId()).isEqualTo(request.getMatchedAssetId());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        DiscoveredAssetResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getJobId()).isEqualTo(entity.getJobId());
        assertThat(response.getAssetType()).isEqualTo(entity.getAssetType());
        assertThat(response.getFingerprint()).isEqualTo(entity.getFingerprint());
        assertThat(response.getRawPayload()).isEqualTo(entity.getRawPayload());
        assertThat(response.getMatchedAssetId()).isEqualTo(entity.getMatchedAssetId());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        DiscoveredAssetSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(DiscoveredAssetFixtures.entity())).hasSameSizeAs(DiscoveredAssetMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        DiscoveredAssetUpdateRequest request = new DiscoveredAssetUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setAssetType("assetType-3");
        request.setFingerprint("fingerprint-3");
        request.setRawPayload("rawPayload-3");
        request.setMatchedAssetId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(DiscoveredAssetLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getJobId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getAssetType()).isEqualTo("assetType-3");
        assertThat(entity.getFingerprint()).isEqualTo("fingerprint-3");
        assertThat(entity.getRawPayload()).isEqualTo("rawPayload-3");
        assertThat(entity.getMatchedAssetId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(DiscoveredAssetLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        DiscoveredAsset entity = DiscoveredAssetFixtures.entity();
        DiscoveredAsset before = DiscoveredAssetFixtures.entity();
        mapper.applyUpdate(new DiscoveredAssetUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
