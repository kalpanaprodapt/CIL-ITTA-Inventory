package com.uniti.cil.itta.inventory.software.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import com.uniti.cil.itta.inventory.software.support.PatchFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatchMapperTest {

    private final PatchMapper mapper = new PatchMapper();

    @Test
    void toEntityCopiesEveryField() {
        PatchCreateRequest request = PatchFixtures.createRequest();
        Patch entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVersionId()).isEqualTo(request.getVersionId());
        assertThat(entity.getPatchId()).isEqualTo(request.getPatchId());
        assertThat(entity.getSeverity()).isEqualTo(request.getSeverity());
        assertThat(entity.getReleasedOn()).isEqualTo(request.getReleasedOn());
        assertThat(entity.getRebootRequired()).isEqualTo(request.getRebootRequired());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        Patch entity = PatchFixtures.entity();
        PatchResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVersionId()).isEqualTo(entity.getVersionId());
        assertThat(response.getPatchId()).isEqualTo(entity.getPatchId());
        assertThat(response.getSeverity()).isEqualTo(entity.getSeverity());
        assertThat(response.getReleasedOn()).isEqualTo(entity.getReleasedOn());
        assertThat(response.getRebootRequired()).isEqualTo(entity.getRebootRequired());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        Patch entity = PatchFixtures.entity();
        PatchSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PatchFixtures.entity())).hasSameSizeAs(PatchMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Patch entity = PatchFixtures.entity();
        PatchUpdateRequest request = new PatchUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVersionId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setPatchId("patchId-3");
        request.setSeverity(PatchSeverity.HIGH);
        request.setReleasedOn(LocalDate.of(2026, 4, 13));
        request.setRebootRequired(Boolean.TRUE);
        request.setCriticality(PatchCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVersionId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getPatchId()).isEqualTo("patchId-3");
        assertThat(entity.getSeverity()).isEqualTo(PatchSeverity.HIGH);
        assertThat(entity.getReleasedOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getRebootRequired()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getCriticality()).isEqualTo(PatchCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Patch entity = PatchFixtures.entity();
        Patch before = PatchFixtures.entity();
        mapper.applyUpdate(new PatchUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
