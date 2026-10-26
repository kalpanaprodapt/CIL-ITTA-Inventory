package com.uniti.cil.itta.inventory.change.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import com.uniti.cil.itta.inventory.change.support.FieldTaskFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FieldTaskMapperTest {

    private final FieldTaskMapper mapper = new FieldTaskMapper();

    @Test
    void toEntityCopiesEveryField() {
        FieldTaskCreateRequest request = FieldTaskFixtures.createRequest();
        FieldTask entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getWorkOrderId()).isEqualTo(request.getWorkOrderId());
        assertThat(entity.getTaskName()).isEqualTo(request.getTaskName());
        assertThat(entity.getSequence()).isEqualTo(request.getSequence());
        assertThat(entity.getDurationMinutes()).isEqualTo(request.getDurationMinutes());
        assertThat(entity.getCompletedOn()).isEqualTo(request.getCompletedOn());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        FieldTask entity = FieldTaskFixtures.entity();
        FieldTaskResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getWorkOrderId()).isEqualTo(entity.getWorkOrderId());
        assertThat(response.getTaskName()).isEqualTo(entity.getTaskName());
        assertThat(response.getSequence()).isEqualTo(entity.getSequence());
        assertThat(response.getDurationMinutes()).isEqualTo(entity.getDurationMinutes());
        assertThat(response.getCompletedOn()).isEqualTo(entity.getCompletedOn());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        FieldTask entity = FieldTaskFixtures.entity();
        FieldTaskSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(FieldTaskFixtures.entity())).hasSameSizeAs(FieldTaskMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        FieldTask entity = FieldTaskFixtures.entity();
        FieldTaskUpdateRequest request = new FieldTaskUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setWorkOrderId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTaskName("taskName-3");
        request.setSequence(13);
        request.setDurationMinutes(13);
        request.setCompletedOn(Instant.parse("2026-04-13T10:15:30Z"));
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(FieldTaskCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getWorkOrderId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTaskName()).isEqualTo("taskName-3");
        assertThat(entity.getSequence()).isEqualTo(13);
        assertThat(entity.getDurationMinutes()).isEqualTo(13);
        assertThat(entity.getCompletedOn()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(FieldTaskCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        FieldTask entity = FieldTaskFixtures.entity();
        FieldTask before = FieldTaskFixtures.entity();
        mapper.applyUpdate(new FieldTaskUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
