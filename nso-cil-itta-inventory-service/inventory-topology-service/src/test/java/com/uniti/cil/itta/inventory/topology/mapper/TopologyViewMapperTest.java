package com.uniti.cil.itta.inventory.topology.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import com.uniti.cil.itta.inventory.topology.support.TopologyViewFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TopologyViewMapperTest {

    private final TopologyViewMapper mapper = new TopologyViewMapper();

    @Test
    void toEntityCopiesEveryField() {
        TopologyViewCreateRequest request = TopologyViewFixtures.createRequest();
        TopologyView entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getViewName()).isEqualTo(request.getViewName());
        assertThat(entity.getViewType()).isEqualTo(request.getViewType());
        assertThat(entity.getScopeFilter()).isEqualTo(request.getScopeFilter());
        assertThat(entity.getRefreshedAt()).isEqualTo(request.getRefreshedAt());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        TopologyView entity = TopologyViewFixtures.entity();
        TopologyViewResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getViewName()).isEqualTo(entity.getViewName());
        assertThat(response.getViewType()).isEqualTo(entity.getViewType());
        assertThat(response.getScopeFilter()).isEqualTo(entity.getScopeFilter());
        assertThat(response.getRefreshedAt()).isEqualTo(entity.getRefreshedAt());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        TopologyView entity = TopologyViewFixtures.entity();
        TopologyViewSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(TopologyViewFixtures.entity())).hasSameSizeAs(TopologyViewMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        TopologyView entity = TopologyViewFixtures.entity();
        TopologyViewUpdateRequest request = new TopologyViewUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setViewName("viewName-3");
        request.setViewType(TopologyViewViewType.L3);
        request.setScopeFilter("scopeFilter-3");
        request.setRefreshedAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(TopologyViewLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getViewName()).isEqualTo("viewName-3");
        assertThat(entity.getViewType()).isEqualTo(TopologyViewViewType.L3);
        assertThat(entity.getScopeFilter()).isEqualTo("scopeFilter-3");
        assertThat(entity.getRefreshedAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(TopologyViewLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        TopologyView entity = TopologyViewFixtures.entity();
        TopologyView before = TopologyViewFixtures.entity();
        mapper.applyUpdate(new TopologyViewUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
