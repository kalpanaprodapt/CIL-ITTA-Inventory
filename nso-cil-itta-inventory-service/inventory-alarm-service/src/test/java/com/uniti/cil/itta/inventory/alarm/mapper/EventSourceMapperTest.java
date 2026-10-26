package com.uniti.cil.itta.inventory.alarm.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import com.uniti.cil.itta.inventory.alarm.support.EventSourceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class EventSourceMapperTest {

    private final EventSourceMapper mapper = new EventSourceMapper();

    @Test
    void toEntityCopiesEveryField() {
        EventSourceCreateRequest request = EventSourceFixtures.createRequest();
        EventSource entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSourceName()).isEqualTo(request.getSourceName());
        assertThat(entity.getProtocol()).isEqualTo(request.getProtocol());
        assertThat(entity.getEndpoint()).isEqualTo(request.getEndpoint());
        assertThat(entity.getHeartbeatSeconds()).isEqualTo(request.getHeartbeatSeconds());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        EventSource entity = EventSourceFixtures.entity();
        EventSourceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSourceName()).isEqualTo(entity.getSourceName());
        assertThat(response.getProtocol()).isEqualTo(entity.getProtocol());
        assertThat(response.getEndpoint()).isEqualTo(entity.getEndpoint());
        assertThat(response.getHeartbeatSeconds()).isEqualTo(entity.getHeartbeatSeconds());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        EventSource entity = EventSourceFixtures.entity();
        EventSourceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(EventSourceFixtures.entity())).hasSameSizeAs(EventSourceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        EventSource entity = EventSourceFixtures.entity();
        EventSourceUpdateRequest request = new EventSourceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSourceName("sourceName-3");
        request.setProtocol(EventSourceProtocol.KAFKA);
        request.setEndpoint("endpoint-3");
        request.setHeartbeatSeconds(13);
        request.setLabels("labels-3");
        request.setLifecycleStage(EventSourceLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSourceName()).isEqualTo("sourceName-3");
        assertThat(entity.getProtocol()).isEqualTo(EventSourceProtocol.KAFKA);
        assertThat(entity.getEndpoint()).isEqualTo("endpoint-3");
        assertThat(entity.getHeartbeatSeconds()).isEqualTo(13);
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(EventSourceLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        EventSource entity = EventSourceFixtures.entity();
        EventSource before = EventSourceFixtures.entity();
        mapper.applyUpdate(new EventSourceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
