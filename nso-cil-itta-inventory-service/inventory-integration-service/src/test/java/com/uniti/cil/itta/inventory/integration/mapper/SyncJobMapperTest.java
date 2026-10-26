package com.uniti.cil.itta.inventory.integration.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import com.uniti.cil.itta.inventory.integration.support.SyncJobFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SyncJobMapperTest {

    private final SyncJobMapper mapper = new SyncJobMapper();

    @Test
    void toEntityCopiesEveryField() {
        SyncJobCreateRequest request = SyncJobFixtures.createRequest();
        SyncJob entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getEndpointId()).isEqualTo(request.getEndpointId());
        assertThat(entity.getDirection()).isEqualTo(request.getDirection());
        assertThat(entity.getScheduleCron()).isEqualTo(request.getScheduleCron());
        assertThat(entity.getLastRunAt()).isEqualTo(request.getLastRunAt());
        assertThat(entity.getLastStatus()).isEqualTo(request.getLastStatus());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        SyncJob entity = SyncJobFixtures.entity();
        SyncJobResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getEndpointId()).isEqualTo(entity.getEndpointId());
        assertThat(response.getDirection()).isEqualTo(entity.getDirection());
        assertThat(response.getScheduleCron()).isEqualTo(entity.getScheduleCron());
        assertThat(response.getLastRunAt()).isEqualTo(entity.getLastRunAt());
        assertThat(response.getLastStatus()).isEqualTo(entity.getLastStatus());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        SyncJob entity = SyncJobFixtures.entity();
        SyncJobSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SyncJobFixtures.entity())).hasSameSizeAs(SyncJobMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        SyncJob entity = SyncJobFixtures.entity();
        SyncJobUpdateRequest request = new SyncJobUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setEndpointId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setDirection(SyncJobDirection.BIDIRECTIONAL);
        request.setScheduleCron("scheduleCron-3");
        request.setLastRunAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setLastStatus(SyncJobLastStatus.FAILED);
        request.setOwner("owner-3");
        request.setCriticality(SyncJobCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getEndpointId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getDirection()).isEqualTo(SyncJobDirection.BIDIRECTIONAL);
        assertThat(entity.getScheduleCron()).isEqualTo("scheduleCron-3");
        assertThat(entity.getLastRunAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getLastStatus()).isEqualTo(SyncJobLastStatus.FAILED);
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SyncJobCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        SyncJob entity = SyncJobFixtures.entity();
        SyncJob before = SyncJobFixtures.entity();
        mapper.applyUpdate(new SyncJobUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
