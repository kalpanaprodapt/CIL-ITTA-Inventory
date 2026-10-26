package com.uniti.cil.itta.inventory.switching.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import com.uniti.cil.itta.inventory.switching.support.VlanFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VlanMapperTest {

    private final VlanMapper mapper = new VlanMapper();

    @Test
    void toEntityCopiesEveryField() {
        VlanCreateRequest request = VlanFixtures.createRequest();
        Vlan entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVlanId()).isEqualTo(request.getVlanId());
        assertThat(entity.getVlanName()).isEqualTo(request.getVlanName());
        assertThat(entity.getVlanType()).isEqualTo(request.getVlanType());
        assertThat(entity.getPoolId()).isEqualTo(request.getPoolId());
        assertThat(entity.getStpMode()).isEqualTo(request.getStpMode());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        Vlan entity = VlanFixtures.entity();
        VlanResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVlanId()).isEqualTo(entity.getVlanId());
        assertThat(response.getVlanName()).isEqualTo(entity.getVlanName());
        assertThat(response.getVlanType()).isEqualTo(entity.getVlanType());
        assertThat(response.getPoolId()).isEqualTo(entity.getPoolId());
        assertThat(response.getStpMode()).isEqualTo(entity.getStpMode());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        Vlan entity = VlanFixtures.entity();
        VlanSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(VlanFixtures.entity())).hasSameSizeAs(VlanMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Vlan entity = VlanFixtures.entity();
        VlanUpdateRequest request = new VlanUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVlanId(13);
        request.setVlanName("vlanName-3");
        request.setVlanType(VlanVlanType.MGMT);
        request.setPoolId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setStpMode("stpMode-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(VlanLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVlanId()).isEqualTo(13);
        assertThat(entity.getVlanName()).isEqualTo("vlanName-3");
        assertThat(entity.getVlanType()).isEqualTo(VlanVlanType.MGMT);
        assertThat(entity.getPoolId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getStpMode()).isEqualTo("stpMode-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(VlanLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Vlan entity = VlanFixtures.entity();
        Vlan before = VlanFixtures.entity();
        mapper.applyUpdate(new VlanUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
