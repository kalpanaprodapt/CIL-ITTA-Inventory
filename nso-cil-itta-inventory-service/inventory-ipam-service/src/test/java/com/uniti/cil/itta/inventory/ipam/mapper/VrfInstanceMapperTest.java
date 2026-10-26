package com.uniti.cil.itta.inventory.ipam.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import com.uniti.cil.itta.inventory.ipam.support.VrfInstanceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VrfInstanceMapperTest {

    private final VrfInstanceMapper mapper = new VrfInstanceMapper();

    @Test
    void toEntityCopiesEveryField() {
        VrfInstanceCreateRequest request = VrfInstanceFixtures.createRequest();
        VrfInstance entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVrfName()).isEqualTo(request.getVrfName());
        assertThat(entity.getRouteDistinguisher()).isEqualTo(request.getRouteDistinguisher());
        assertThat(entity.getImportTargets()).isEqualTo(request.getImportTargets());
        assertThat(entity.getExportTargets()).isEqualTo(request.getExportTargets());
        assertThat(entity.getDeviceId()).isEqualTo(request.getDeviceId());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        VrfInstanceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVrfName()).isEqualTo(entity.getVrfName());
        assertThat(response.getRouteDistinguisher()).isEqualTo(entity.getRouteDistinguisher());
        assertThat(response.getImportTargets()).isEqualTo(entity.getImportTargets());
        assertThat(response.getExportTargets()).isEqualTo(entity.getExportTargets());
        assertThat(response.getDeviceId()).isEqualTo(entity.getDeviceId());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        VrfInstanceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(VrfInstanceFixtures.entity())).hasSameSizeAs(VrfInstanceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        VrfInstanceUpdateRequest request = new VrfInstanceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVrfName("vrfName-3");
        request.setRouteDistinguisher("routeDistinguisher-3");
        request.setImportTargets("importTargets-3");
        request.setExportTargets("exportTargets-3");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLabels("labels-3");
        request.setLifecycleStage(VrfInstanceLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVrfName()).isEqualTo("vrfName-3");
        assertThat(entity.getRouteDistinguisher()).isEqualTo("routeDistinguisher-3");
        assertThat(entity.getImportTargets()).isEqualTo("importTargets-3");
        assertThat(entity.getExportTargets()).isEqualTo("exportTargets-3");
        assertThat(entity.getDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(VrfInstanceLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        VrfInstance entity = VrfInstanceFixtures.entity();
        VrfInstance before = VrfInstanceFixtures.entity();
        mapper.applyUpdate(new VrfInstanceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
