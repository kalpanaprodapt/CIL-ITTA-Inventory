package com.uniti.cil.itta.inventory.software.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import com.uniti.cil.itta.inventory.software.support.SoftwareImageFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SoftwareImageMapperTest {

    private final SoftwareImageMapper mapper = new SoftwareImageMapper();

    @Test
    void toEntityCopiesEveryField() {
        SoftwareImageCreateRequest request = SoftwareImageFixtures.createRequest();
        SoftwareImage entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVendorId()).isEqualTo(request.getVendorId());
        assertThat(entity.getImageName()).isEqualTo(request.getImageName());
        assertThat(entity.getSizeMb()).isEqualTo(request.getSizeMb());
        assertThat(entity.getChecksum()).isEqualTo(request.getChecksum());
        assertThat(entity.getPlatform()).isEqualTo(request.getPlatform());
        assertThat(entity.getStoragePath()).isEqualTo(request.getStoragePath());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        SoftwareImageResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVendorId()).isEqualTo(entity.getVendorId());
        assertThat(response.getImageName()).isEqualTo(entity.getImageName());
        assertThat(response.getSizeMb()).isEqualTo(entity.getSizeMb());
        assertThat(response.getChecksum()).isEqualTo(entity.getChecksum());
        assertThat(response.getPlatform()).isEqualTo(entity.getPlatform());
        assertThat(response.getStoragePath()).isEqualTo(entity.getStoragePath());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        SoftwareImageSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SoftwareImageFixtures.entity())).hasSameSizeAs(SoftwareImageMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        SoftwareImageUpdateRequest request = new SoftwareImageUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setImageName("imageName-3");
        request.setSizeMb(1003L);
        request.setChecksum("checksum-3");
        request.setPlatform("platform-3");
        request.setStoragePath("storagePath-3");
        request.setLifecycleStage(SoftwareImageLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(SoftwareImageCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVendorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getImageName()).isEqualTo("imageName-3");
        assertThat(entity.getSizeMb()).isEqualTo(1003L);
        assertThat(entity.getChecksum()).isEqualTo("checksum-3");
        assertThat(entity.getPlatform()).isEqualTo("platform-3");
        assertThat(entity.getStoragePath()).isEqualTo("storagePath-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(SoftwareImageLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SoftwareImageCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        SoftwareImage entity = SoftwareImageFixtures.entity();
        SoftwareImage before = SoftwareImageFixtures.entity();
        mapper.applyUpdate(new SoftwareImageUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
