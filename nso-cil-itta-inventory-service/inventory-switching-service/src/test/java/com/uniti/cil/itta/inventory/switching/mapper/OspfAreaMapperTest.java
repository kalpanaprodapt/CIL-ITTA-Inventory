package com.uniti.cil.itta.inventory.switching.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import com.uniti.cil.itta.inventory.switching.support.OspfAreaFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class OspfAreaMapperTest {

    private final OspfAreaMapper mapper = new OspfAreaMapper();

    @Test
    void toEntityCopiesEveryField() {
        OspfAreaCreateRequest request = OspfAreaFixtures.createRequest();
        OspfArea entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAreaId()).isEqualTo(request.getAreaId());
        assertThat(entity.getAreaType()).isEqualTo(request.getAreaType());
        assertThat(entity.getDeviceCount()).isEqualTo(request.getDeviceCount());
        assertThat(entity.getAuthType()).isEqualTo(request.getAuthType());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        OspfArea entity = OspfAreaFixtures.entity();
        OspfAreaResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAreaId()).isEqualTo(entity.getAreaId());
        assertThat(response.getAreaType()).isEqualTo(entity.getAreaType());
        assertThat(response.getDeviceCount()).isEqualTo(entity.getDeviceCount());
        assertThat(response.getAuthType()).isEqualTo(entity.getAuthType());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        OspfArea entity = OspfAreaFixtures.entity();
        OspfAreaSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(OspfAreaFixtures.entity())).hasSameSizeAs(OspfAreaMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        OspfArea entity = OspfAreaFixtures.entity();
        OspfAreaUpdateRequest request = new OspfAreaUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAreaId("areaId-3");
        request.setAreaType(OspfAreaAreaType.STUB);
        request.setDeviceCount(13);
        request.setAuthType("authType-3");
        request.setCriticality(OspfAreaCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getAreaId()).isEqualTo("areaId-3");
        assertThat(entity.getAreaType()).isEqualTo(OspfAreaAreaType.STUB);
        assertThat(entity.getDeviceCount()).isEqualTo(13);
        assertThat(entity.getAuthType()).isEqualTo("authType-3");
        assertThat(entity.getCriticality()).isEqualTo(OspfAreaCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        OspfArea entity = OspfAreaFixtures.entity();
        OspfArea before = OspfAreaFixtures.entity();
        mapper.applyUpdate(new OspfAreaUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
