package com.uniti.cil.itta.inventory.cabling.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.*;
import com.uniti.cil.itta.inventory.cabling.support.PatchPanelFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatchPanelMapperTest {

    private final PatchPanelMapper mapper = new PatchPanelMapper();

    @Test
    void toEntityCopiesEveryField() {
        PatchPanelCreateRequest request = PatchPanelFixtures.createRequest();
        PatchPanel entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRoomId()).isEqualTo(request.getRoomId());
        assertThat(entity.getRackId()).isEqualTo(request.getRackId());
        assertThat(entity.getPanelType()).isEqualTo(request.getPanelType());
        assertThat(entity.getPortCount()).isEqualTo(request.getPortCount());
        assertThat(entity.getLabel()).isEqualTo(request.getLabel());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        PatchPanel entity = PatchPanelFixtures.entity();
        PatchPanelResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRoomId()).isEqualTo(entity.getRoomId());
        assertThat(response.getRackId()).isEqualTo(entity.getRackId());
        assertThat(response.getPanelType()).isEqualTo(entity.getPanelType());
        assertThat(response.getPortCount()).isEqualTo(entity.getPortCount());
        assertThat(response.getLabel()).isEqualTo(entity.getLabel());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        PatchPanel entity = PatchPanelFixtures.entity();
        PatchPanelSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PatchPanelFixtures.entity())).hasSameSizeAs(PatchPanelMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        PatchPanel entity = PatchPanelFixtures.entity();
        PatchPanelUpdateRequest request = new PatchPanelUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setRackId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setPanelType(PatchPanelPanelType.COPPER);
        request.setPortCount(13);
        request.setLabel("label-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(PatchPanelLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRoomId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getRackId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getPanelType()).isEqualTo(PatchPanelPanelType.COPPER);
        assertThat(entity.getPortCount()).isEqualTo(13);
        assertThat(entity.getLabel()).isEqualTo("label-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(PatchPanelLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        PatchPanel entity = PatchPanelFixtures.entity();
        PatchPanel before = PatchPanelFixtures.entity();
        mapper.applyUpdate(new PatchPanelUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
