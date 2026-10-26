package com.uniti.cil.itta.inventory.topology.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import com.uniti.cil.itta.inventory.topology.support.TopologyEdgeFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TopologyEdgeMapperTest {

    private final TopologyEdgeMapper mapper = new TopologyEdgeMapper();

    @Test
    void toEntityCopiesEveryField() {
        TopologyEdgeCreateRequest request = TopologyEdgeFixtures.createRequest();
        TopologyEdge entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getViewId()).isEqualTo(request.getViewId());
        assertThat(entity.getSourceNodeId()).isEqualTo(request.getSourceNodeId());
        assertThat(entity.getTargetNodeId()).isEqualTo(request.getTargetNodeId());
        assertThat(entity.getWeight()).isEqualTo(request.getWeight());
        assertThat(entity.getDirected()).isEqualTo(request.getDirected());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        TopologyEdgeResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getViewId()).isEqualTo(entity.getViewId());
        assertThat(response.getSourceNodeId()).isEqualTo(entity.getSourceNodeId());
        assertThat(response.getTargetNodeId()).isEqualTo(entity.getTargetNodeId());
        assertThat(response.getWeight()).isEqualTo(entity.getWeight());
        assertThat(response.getDirected()).isEqualTo(entity.getDirected());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        TopologyEdgeSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(TopologyEdgeFixtures.entity())).hasSameSizeAs(TopologyEdgeMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        TopologyEdgeUpdateRequest request = new TopologyEdgeUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setViewId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSourceNodeId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTargetNodeId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setWeight(new BigDecimal("15.50"));
        request.setDirected(Boolean.TRUE);
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(TopologyEdgeLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getViewId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSourceNodeId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTargetNodeId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getWeight()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getDirected()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(TopologyEdgeLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        TopologyEdge entity = TopologyEdgeFixtures.entity();
        TopologyEdge before = TopologyEdgeFixtures.entity();
        mapper.applyUpdate(new TopologyEdgeUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
