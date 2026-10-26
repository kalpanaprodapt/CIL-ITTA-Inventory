package com.uniti.cil.itta.inventory.software.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import com.uniti.cil.itta.inventory.software.support.SoftwareVersionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SoftwareVersionMapperTest {

    private final SoftwareVersionMapper mapper = new SoftwareVersionMapper();

    @Test
    void toEntityCopiesEveryField() {
        SoftwareVersionCreateRequest request = SoftwareVersionFixtures.createRequest();
        SoftwareVersion entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getImageId()).isEqualTo(request.getImageId());
        assertThat(entity.getVersionString()).isEqualTo(request.getVersionString());
        assertThat(entity.getReleaseDate()).isEqualTo(request.getReleaseDate());
        assertThat(entity.getEndOfSupport()).isEqualTo(request.getEndOfSupport());
        assertThat(entity.getReleaseTrain()).isEqualTo(request.getReleaseTrain());
        assertThat(entity.getCveCount()).isEqualTo(request.getCveCount());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        SoftwareVersionResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getImageId()).isEqualTo(entity.getImageId());
        assertThat(response.getVersionString()).isEqualTo(entity.getVersionString());
        assertThat(response.getReleaseDate()).isEqualTo(entity.getReleaseDate());
        assertThat(response.getEndOfSupport()).isEqualTo(entity.getEndOfSupport());
        assertThat(response.getReleaseTrain()).isEqualTo(entity.getReleaseTrain());
        assertThat(response.getCveCount()).isEqualTo(entity.getCveCount());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        SoftwareVersionSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SoftwareVersionFixtures.entity())).hasSameSizeAs(SoftwareVersionMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        SoftwareVersionUpdateRequest request = new SoftwareVersionUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setImageId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setVersionString("versionString-3");
        request.setReleaseDate(LocalDate.of(2026, 4, 13));
        request.setEndOfSupport(LocalDate.of(2026, 4, 13));
        request.setReleaseTrain(SoftwareVersionReleaseTrain.EARLY);
        request.setCveCount(13);
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(SoftwareVersionCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getImageId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getVersionString()).isEqualTo("versionString-3");
        assertThat(entity.getReleaseDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEndOfSupport()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getReleaseTrain()).isEqualTo(SoftwareVersionReleaseTrain.EARLY);
        assertThat(entity.getCveCount()).isEqualTo(13);
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SoftwareVersionCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        SoftwareVersion entity = SoftwareVersionFixtures.entity();
        SoftwareVersion before = SoftwareVersionFixtures.entity();
        mapper.applyUpdate(new SoftwareVersionUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
