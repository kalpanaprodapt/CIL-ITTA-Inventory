package com.uniti.cil.itta.inventory.radio.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.radio.dto.*;
import com.uniti.cil.itta.inventory.radio.model.*;
import com.uniti.cil.itta.inventory.radio.support.BaseStationFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BaseStationMapperTest {

    private final BaseStationMapper mapper = new BaseStationMapper();

    @Test
    void toEntityCopiesEveryField() {
        BaseStationCreateRequest request = BaseStationFixtures.createRequest();
        BaseStation entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSiteId()).isEqualTo(request.getSiteId());
        assertThat(entity.getEnbId()).isEqualTo(request.getEnbId());
        assertThat(entity.getTechnology()).isEqualTo(request.getTechnology());
        assertThat(entity.getSectorCount()).isEqualTo(request.getSectorCount());
        assertThat(entity.getCellCount()).isEqualTo(request.getCellCount());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        BaseStation entity = BaseStationFixtures.entity();
        BaseStationResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSiteId()).isEqualTo(entity.getSiteId());
        assertThat(response.getEnbId()).isEqualTo(entity.getEnbId());
        assertThat(response.getTechnology()).isEqualTo(entity.getTechnology());
        assertThat(response.getSectorCount()).isEqualTo(entity.getSectorCount());
        assertThat(response.getCellCount()).isEqualTo(entity.getCellCount());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        BaseStation entity = BaseStationFixtures.entity();
        BaseStationSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(BaseStationFixtures.entity())).hasSameSizeAs(BaseStationMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        BaseStation entity = BaseStationFixtures.entity();
        BaseStationUpdateRequest request = new BaseStationUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setEnbId(1003L);
        request.setTechnology(BaseStationTechnology.GSM);
        request.setSectorCount(13);
        request.setCellCount(13);
        request.setLabels("labels-3");
        request.setLifecycleStage(BaseStationLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSiteId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getEnbId()).isEqualTo(1003L);
        assertThat(entity.getTechnology()).isEqualTo(BaseStationTechnology.GSM);
        assertThat(entity.getSectorCount()).isEqualTo(13);
        assertThat(entity.getCellCount()).isEqualTo(13);
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(BaseStationLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        BaseStation entity = BaseStationFixtures.entity();
        BaseStation before = BaseStationFixtures.entity();
        mapper.applyUpdate(new BaseStationUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
