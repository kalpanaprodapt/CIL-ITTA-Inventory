package com.uniti.cil.itta.inventory.topology.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import com.uniti.cil.itta.inventory.topology.support.PathTraceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PathTraceMapperTest {

    private final PathTraceMapper mapper = new PathTraceMapper();

    @Test
    void toEntityCopiesEveryField() {
        PathTraceCreateRequest request = PathTraceFixtures.createRequest();
        PathTrace entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSourceRef()).isEqualTo(request.getSourceRef());
        assertThat(entity.getTargetRef()).isEqualTo(request.getTargetRef());
        assertThat(entity.getHopCount()).isEqualTo(request.getHopCount());
        assertThat(entity.getTotalLatencyMs()).isEqualTo(request.getTotalLatencyMs());
        assertThat(entity.getTraceMethod()).isEqualTo(request.getTraceMethod());
        assertThat(entity.getTracedAt()).isEqualTo(request.getTracedAt());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        PathTrace entity = PathTraceFixtures.entity();
        PathTraceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSourceRef()).isEqualTo(entity.getSourceRef());
        assertThat(response.getTargetRef()).isEqualTo(entity.getTargetRef());
        assertThat(response.getHopCount()).isEqualTo(entity.getHopCount());
        assertThat(response.getTotalLatencyMs()).isEqualTo(entity.getTotalLatencyMs());
        assertThat(response.getTraceMethod()).isEqualTo(entity.getTraceMethod());
        assertThat(response.getTracedAt()).isEqualTo(entity.getTracedAt());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        PathTrace entity = PathTraceFixtures.entity();
        PathTraceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PathTraceFixtures.entity())).hasSameSizeAs(PathTraceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        PathTrace entity = PathTraceFixtures.entity();
        PathTraceUpdateRequest request = new PathTraceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSourceRef("sourceRef-3");
        request.setTargetRef("targetRef-3");
        request.setHopCount(13);
        request.setTotalLatencyMs(new BigDecimal("15.50"));
        request.setTraceMethod("traceMethod-3");
        request.setTracedAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setLifecycleStage(PathTraceLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(PathTraceCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSourceRef()).isEqualTo("sourceRef-3");
        assertThat(entity.getTargetRef()).isEqualTo("targetRef-3");
        assertThat(entity.getHopCount()).isEqualTo(13);
        assertThat(entity.getTotalLatencyMs()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getTraceMethod()).isEqualTo("traceMethod-3");
        assertThat(entity.getTracedAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getLifecycleStage()).isEqualTo(PathTraceLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(PathTraceCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        PathTrace entity = PathTraceFixtures.entity();
        PathTrace before = PathTraceFixtures.entity();
        mapper.applyUpdate(new PathTraceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
