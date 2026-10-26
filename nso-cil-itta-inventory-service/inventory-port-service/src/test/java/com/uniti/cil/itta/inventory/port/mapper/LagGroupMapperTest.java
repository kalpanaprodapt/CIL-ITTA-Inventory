package com.uniti.cil.itta.inventory.port.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import com.uniti.cil.itta.inventory.port.support.LagGroupFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LagGroupMapperTest {

    private final LagGroupMapper mapper = new LagGroupMapper();

    @Test
    void toEntityCopiesEveryField() {
        LagGroupCreateRequest request = LagGroupFixtures.createRequest();
        LagGroup entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getDeviceId()).isEqualTo(request.getDeviceId());
        assertThat(entity.getLagName()).isEqualTo(request.getLagName());
        assertThat(entity.getMemberCount()).isEqualTo(request.getMemberCount());
        assertThat(entity.getLacpMode()).isEqualTo(request.getLacpMode());
        assertThat(entity.getMinLinks()).isEqualTo(request.getMinLinks());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        LagGroup entity = LagGroupFixtures.entity();
        LagGroupResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getDeviceId()).isEqualTo(entity.getDeviceId());
        assertThat(response.getLagName()).isEqualTo(entity.getLagName());
        assertThat(response.getMemberCount()).isEqualTo(entity.getMemberCount());
        assertThat(response.getLacpMode()).isEqualTo(entity.getLacpMode());
        assertThat(response.getMinLinks()).isEqualTo(entity.getMinLinks());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        LagGroup entity = LagGroupFixtures.entity();
        LagGroupSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(LagGroupFixtures.entity())).hasSameSizeAs(LagGroupMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        LagGroup entity = LagGroupFixtures.entity();
        LagGroupUpdateRequest request = new LagGroupUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLagName("lagName-3");
        request.setMemberCount(13);
        request.setLacpMode(LagGroupLacpMode.STATIC);
        request.setMinLinks(13);
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(LagGroupLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLagName()).isEqualTo("lagName-3");
        assertThat(entity.getMemberCount()).isEqualTo(13);
        assertThat(entity.getLacpMode()).isEqualTo(LagGroupLacpMode.STATIC);
        assertThat(entity.getMinLinks()).isEqualTo(13);
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(LagGroupLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        LagGroup entity = LagGroupFixtures.entity();
        LagGroup before = LagGroupFixtures.entity();
        mapper.applyUpdate(new LagGroupUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
