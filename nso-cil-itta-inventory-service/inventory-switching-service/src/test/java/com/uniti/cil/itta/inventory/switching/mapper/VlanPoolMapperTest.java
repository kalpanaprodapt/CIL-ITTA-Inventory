package com.uniti.cil.itta.inventory.switching.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import com.uniti.cil.itta.inventory.switching.support.VlanPoolFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VlanPoolMapperTest {

    private final VlanPoolMapper mapper = new VlanPoolMapper();

    @Test
    void toEntityCopiesEveryField() {
        VlanPoolCreateRequest request = VlanPoolFixtures.createRequest();
        VlanPool entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getPoolName()).isEqualTo(request.getPoolName());
        assertThat(entity.getRangeStart()).isEqualTo(request.getRangeStart());
        assertThat(entity.getRangeEnd()).isEqualTo(request.getRangeEnd());
        assertThat(entity.getAllocated()).isEqualTo(request.getAllocated());
        assertThat(entity.getScope()).isEqualTo(request.getScope());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        VlanPool entity = VlanPoolFixtures.entity();
        VlanPoolResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getPoolName()).isEqualTo(entity.getPoolName());
        assertThat(response.getRangeStart()).isEqualTo(entity.getRangeStart());
        assertThat(response.getRangeEnd()).isEqualTo(entity.getRangeEnd());
        assertThat(response.getAllocated()).isEqualTo(entity.getAllocated());
        assertThat(response.getScope()).isEqualTo(entity.getScope());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        VlanPool entity = VlanPoolFixtures.entity();
        VlanPoolSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(VlanPoolFixtures.entity())).hasSameSizeAs(VlanPoolMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        VlanPool entity = VlanPoolFixtures.entity();
        VlanPoolUpdateRequest request = new VlanPoolUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setPoolName("poolName-3");
        request.setRangeStart(13);
        request.setRangeEnd(13);
        request.setAllocated(13);
        request.setScope("scope-3");
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(VlanPoolLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getPoolName()).isEqualTo("poolName-3");
        assertThat(entity.getRangeStart()).isEqualTo(13);
        assertThat(entity.getRangeEnd()).isEqualTo(13);
        assertThat(entity.getAllocated()).isEqualTo(13);
        assertThat(entity.getScope()).isEqualTo("scope-3");
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(VlanPoolLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        VlanPool entity = VlanPoolFixtures.entity();
        VlanPool before = VlanPoolFixtures.entity();
        mapper.applyUpdate(new VlanPoolUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
