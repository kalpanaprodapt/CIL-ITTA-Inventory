package com.uniti.cil.itta.inventory.discovery.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveryJobFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscoveryJobMapperTest {

    private final DiscoveryJobMapper mapper = new DiscoveryJobMapper();

    @Test
    void toEntityCopiesEveryField() {
        DiscoveryJobCreateRequest request = DiscoveryJobFixtures.createRequest();
        DiscoveryJob entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getProfileId()).isEqualTo(request.getProfileId());
        assertThat(entity.getStartedAt()).isEqualTo(request.getStartedAt());
        assertThat(entity.getFinishedAt()).isEqualTo(request.getFinishedAt());
        assertThat(entity.getJobState()).isEqualTo(request.getJobState());
        assertThat(entity.getAssetsFound()).isEqualTo(request.getAssetsFound());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        DiscoveryJobResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getProfileId()).isEqualTo(entity.getProfileId());
        assertThat(response.getStartedAt()).isEqualTo(entity.getStartedAt());
        assertThat(response.getFinishedAt()).isEqualTo(entity.getFinishedAt());
        assertThat(response.getJobState()).isEqualTo(entity.getJobState());
        assertThat(response.getAssetsFound()).isEqualTo(entity.getAssetsFound());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        DiscoveryJobSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(DiscoveryJobFixtures.entity())).hasSameSizeAs(DiscoveryJobMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        DiscoveryJobUpdateRequest request = new DiscoveryJobUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setProfileId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setStartedAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setFinishedAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setJobState(DiscoveryJobJobState.SUCCEEDED);
        request.setAssetsFound(13);
        request.setOwner("owner-3");
        request.setCriticality(DiscoveryJobCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getProfileId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getStartedAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getFinishedAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getJobState()).isEqualTo(DiscoveryJobJobState.SUCCEEDED);
        assertThat(entity.getAssetsFound()).isEqualTo(13);
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(DiscoveryJobCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        DiscoveryJob entity = DiscoveryJobFixtures.entity();
        DiscoveryJob before = DiscoveryJobFixtures.entity();
        mapper.applyUpdate(new DiscoveryJobUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
