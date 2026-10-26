package com.uniti.cil.itta.inventory.topology.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import com.uniti.cil.itta.inventory.topology.support.TopologyNodeFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TopologyNodeMapperTest {

    private final TopologyNodeMapper mapper = new TopologyNodeMapper();

    @Test
    void toEntityCopiesEveryField() {
        TopologyNodeCreateRequest request = TopologyNodeFixtures.createRequest();
        TopologyNode entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getViewId()).isEqualTo(request.getViewId());
        assertThat(entity.getEntityRef()).isEqualTo(request.getEntityRef());
        assertThat(entity.getNodeType()).isEqualTo(request.getNodeType());
        assertThat(entity.getXPos()).isEqualTo(request.getXPos());
        assertThat(entity.getYPos()).isEqualTo(request.getYPos());
        assertThat(entity.getLayer()).isEqualTo(request.getLayer());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        TopologyNode entity = TopologyNodeFixtures.entity();
        TopologyNodeResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getViewId()).isEqualTo(entity.getViewId());
        assertThat(response.getEntityRef()).isEqualTo(entity.getEntityRef());
        assertThat(response.getNodeType()).isEqualTo(entity.getNodeType());
        assertThat(response.getXPos()).isEqualTo(entity.getXPos());
        assertThat(response.getYPos()).isEqualTo(entity.getYPos());
        assertThat(response.getLayer()).isEqualTo(entity.getLayer());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        TopologyNode entity = TopologyNodeFixtures.entity();
        TopologyNodeSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(TopologyNodeFixtures.entity())).hasSameSizeAs(TopologyNodeMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        TopologyNode entity = TopologyNodeFixtures.entity();
        TopologyNodeUpdateRequest request = new TopologyNodeUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setViewId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setEntityRef("entityRef-3");
        request.setNodeType("nodeType-3");
        request.setXPos(new BigDecimal("15.50"));
        request.setYPos(new BigDecimal("15.50"));
        request.setLayer(13);
        request.setCriticality(TopologyNodeCriticality.HIGH);
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
        assertThat(entity.getViewId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getEntityRef()).isEqualTo("entityRef-3");
        assertThat(entity.getNodeType()).isEqualTo("nodeType-3");
        assertThat(entity.getXPos()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getYPos()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getLayer()).isEqualTo(13);
        assertThat(entity.getCriticality()).isEqualTo(TopologyNodeCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        TopologyNode entity = TopologyNodeFixtures.entity();
        TopologyNode before = TopologyNodeFixtures.entity();
        mapper.applyUpdate(new TopologyNodeUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
