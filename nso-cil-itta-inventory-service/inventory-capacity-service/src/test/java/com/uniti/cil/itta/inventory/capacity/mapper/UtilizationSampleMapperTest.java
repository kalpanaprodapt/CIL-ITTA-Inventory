package com.uniti.cil.itta.inventory.capacity.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import com.uniti.cil.itta.inventory.capacity.support.UtilizationSampleFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class UtilizationSampleMapperTest {

    private final UtilizationSampleMapper mapper = new UtilizationSampleMapper();

    @Test
    void toEntityCopiesEveryField() {
        UtilizationSampleCreateRequest request = UtilizationSampleFixtures.createRequest();
        UtilizationSample entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getTargetRef()).isEqualTo(request.getTargetRef());
        assertThat(entity.getMetric()).isEqualTo(request.getMetric());
        assertThat(entity.getSampledAt()).isEqualTo(request.getSampledAt());
        assertThat(entity.getValuePct()).isEqualTo(request.getValuePct());
        assertThat(entity.getPeak()).isEqualTo(request.getPeak());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        UtilizationSampleResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getTargetRef()).isEqualTo(entity.getTargetRef());
        assertThat(response.getMetric()).isEqualTo(entity.getMetric());
        assertThat(response.getSampledAt()).isEqualTo(entity.getSampledAt());
        assertThat(response.getValuePct()).isEqualTo(entity.getValuePct());
        assertThat(response.getPeak()).isEqualTo(entity.getPeak());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        UtilizationSampleSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(UtilizationSampleFixtures.entity())).hasSameSizeAs(UtilizationSampleMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        UtilizationSampleUpdateRequest request = new UtilizationSampleUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setTargetRef("targetRef-3");
        request.setMetric("metric-3");
        request.setSampledAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setValuePct(new BigDecimal("15.50"));
        request.setPeak(Boolean.TRUE);
        request.setLifecycleStage(UtilizationSampleLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(UtilizationSampleCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getTargetRef()).isEqualTo("targetRef-3");
        assertThat(entity.getMetric()).isEqualTo("metric-3");
        assertThat(entity.getSampledAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getValuePct()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getPeak()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getLifecycleStage()).isEqualTo(UtilizationSampleLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(UtilizationSampleCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        UtilizationSample entity = UtilizationSampleFixtures.entity();
        UtilizationSample before = UtilizationSampleFixtures.entity();
        mapper.applyUpdate(new UtilizationSampleUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
