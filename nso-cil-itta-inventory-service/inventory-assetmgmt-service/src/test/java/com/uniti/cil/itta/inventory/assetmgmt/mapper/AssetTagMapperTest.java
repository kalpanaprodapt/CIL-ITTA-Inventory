package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import com.uniti.cil.itta.inventory.assetmgmt.support.AssetTagFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AssetTagMapperTest {

    private final AssetTagMapper mapper = new AssetTagMapper();

    @Test
    void toEntityCopiesEveryField() {
        AssetTagCreateRequest request = AssetTagFixtures.createRequest();
        AssetTag entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAssetId()).isEqualTo(request.getAssetId());
        assertThat(entity.getTagValue()).isEqualTo(request.getTagValue());
        assertThat(entity.getTagType()).isEqualTo(request.getTagType());
        assertThat(entity.getPrintedOn()).isEqualTo(request.getPrintedOn());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        AssetTag entity = AssetTagFixtures.entity();
        AssetTagResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAssetId()).isEqualTo(entity.getAssetId());
        assertThat(response.getTagValue()).isEqualTo(entity.getTagValue());
        assertThat(response.getTagType()).isEqualTo(entity.getTagType());
        assertThat(response.getPrintedOn()).isEqualTo(entity.getPrintedOn());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        AssetTag entity = AssetTagFixtures.entity();
        AssetTagSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(AssetTagFixtures.entity())).hasSameSizeAs(AssetTagMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        AssetTag entity = AssetTagFixtures.entity();
        AssetTagUpdateRequest request = new AssetTagUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTagValue("tagValue-3");
        request.setTagType(AssetTagTagType.QR);
        request.setPrintedOn(LocalDate.of(2026, 4, 13));
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(AssetTagCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getAssetId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTagValue()).isEqualTo("tagValue-3");
        assertThat(entity.getTagType()).isEqualTo(AssetTagTagType.QR);
        assertThat(entity.getPrintedOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(AssetTagCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        AssetTag entity = AssetTagFixtures.entity();
        AssetTag before = AssetTagFixtures.entity();
        mapper.applyUpdate(new AssetTagUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
