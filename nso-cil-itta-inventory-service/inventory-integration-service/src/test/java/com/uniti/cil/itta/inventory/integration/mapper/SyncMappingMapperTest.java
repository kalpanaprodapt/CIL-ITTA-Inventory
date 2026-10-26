package com.uniti.cil.itta.inventory.integration.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import com.uniti.cil.itta.inventory.integration.support.SyncMappingFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SyncMappingMapperTest {

    private final SyncMappingMapper mapper = new SyncMappingMapper();

    @Test
    void toEntityCopiesEveryField() {
        SyncMappingCreateRequest request = SyncMappingFixtures.createRequest();
        SyncMapping entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getJobId()).isEqualTo(request.getJobId());
        assertThat(entity.getSourceField()).isEqualTo(request.getSourceField());
        assertThat(entity.getTargetField()).isEqualTo(request.getTargetField());
        assertThat(entity.getTransform()).isEqualTo(request.getTransform());
        assertThat(entity.getRequired()).isEqualTo(request.getRequired());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        SyncMapping entity = SyncMappingFixtures.entity();
        SyncMappingResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getJobId()).isEqualTo(entity.getJobId());
        assertThat(response.getSourceField()).isEqualTo(entity.getSourceField());
        assertThat(response.getTargetField()).isEqualTo(entity.getTargetField());
        assertThat(response.getTransform()).isEqualTo(entity.getTransform());
        assertThat(response.getRequired()).isEqualTo(entity.getRequired());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        SyncMapping entity = SyncMappingFixtures.entity();
        SyncMappingSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SyncMappingFixtures.entity())).hasSameSizeAs(SyncMappingMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        SyncMapping entity = SyncMappingFixtures.entity();
        SyncMappingUpdateRequest request = new SyncMappingUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setJobId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSourceField("sourceField-3");
        request.setTargetField("targetField-3");
        request.setTransform("transform-3");
        request.setRequired(Boolean.TRUE);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getJobId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSourceField()).isEqualTo("sourceField-3");
        assertThat(entity.getTargetField()).isEqualTo("targetField-3");
        assertThat(entity.getTransform()).isEqualTo("transform-3");
        assertThat(entity.getRequired()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        SyncMapping entity = SyncMappingFixtures.entity();
        SyncMapping before = SyncMappingFixtures.entity();
        mapper.applyUpdate(new SyncMappingUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
