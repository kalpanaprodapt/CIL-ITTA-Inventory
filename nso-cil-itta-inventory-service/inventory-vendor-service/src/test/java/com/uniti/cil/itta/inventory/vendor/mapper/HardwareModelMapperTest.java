package com.uniti.cil.itta.inventory.vendor.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import com.uniti.cil.itta.inventory.vendor.support.HardwareModelFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class HardwareModelMapperTest {

    private final HardwareModelMapper mapper = new HardwareModelMapper();

    @Test
    void toEntityCopiesEveryField() {
        HardwareModelCreateRequest request = HardwareModelFixtures.createRequest();
        HardwareModel entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVendorId()).isEqualTo(request.getVendorId());
        assertThat(entity.getModelNumber()).isEqualTo(request.getModelNumber());
        assertThat(entity.getFormFactor()).isEqualTo(request.getFormFactor());
        assertThat(entity.getPowerWatts()).isEqualTo(request.getPowerWatts());
        assertThat(entity.getEolDate()).isEqualTo(request.getEolDate());
        assertThat(entity.getEosDate()).isEqualTo(request.getEosDate());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        HardwareModel entity = HardwareModelFixtures.entity();
        HardwareModelResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVendorId()).isEqualTo(entity.getVendorId());
        assertThat(response.getModelNumber()).isEqualTo(entity.getModelNumber());
        assertThat(response.getFormFactor()).isEqualTo(entity.getFormFactor());
        assertThat(response.getPowerWatts()).isEqualTo(entity.getPowerWatts());
        assertThat(response.getEolDate()).isEqualTo(entity.getEolDate());
        assertThat(response.getEosDate()).isEqualTo(entity.getEosDate());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        HardwareModel entity = HardwareModelFixtures.entity();
        HardwareModelSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(HardwareModelFixtures.entity())).hasSameSizeAs(HardwareModelMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        HardwareModel entity = HardwareModelFixtures.entity();
        HardwareModelUpdateRequest request = new HardwareModelUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setModelNumber("modelNumber-3");
        request.setFormFactor("formFactor-3");
        request.setPowerWatts(13);
        request.setEolDate(LocalDate.of(2026, 4, 13));
        request.setEosDate(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(HardwareModelLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVendorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getModelNumber()).isEqualTo("modelNumber-3");
        assertThat(entity.getFormFactor()).isEqualTo("formFactor-3");
        assertThat(entity.getPowerWatts()).isEqualTo(13);
        assertThat(entity.getEolDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEosDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(HardwareModelLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        HardwareModel entity = HardwareModelFixtures.entity();
        HardwareModel before = HardwareModelFixtures.entity();
        mapper.applyUpdate(new HardwareModelUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
