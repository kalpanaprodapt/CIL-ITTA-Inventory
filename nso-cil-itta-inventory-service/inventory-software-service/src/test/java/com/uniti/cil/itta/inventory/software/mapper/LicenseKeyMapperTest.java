package com.uniti.cil.itta.inventory.software.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import com.uniti.cil.itta.inventory.software.support.LicenseKeyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LicenseKeyMapperTest {

    private final LicenseKeyMapper mapper = new LicenseKeyMapper();

    @Test
    void toEntityCopiesEveryField() {
        LicenseKeyCreateRequest request = LicenseKeyFixtures.createRequest();
        LicenseKey entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getPoolId()).isEqualTo(request.getPoolId());
        assertThat(entity.getKeyValue()).isEqualTo(request.getKeyValue());
        assertThat(entity.getFeature()).isEqualTo(request.getFeature());
        assertThat(entity.getExpiresOn()).isEqualTo(request.getExpiresOn());
        assertThat(entity.getAssignedDeviceId()).isEqualTo(request.getAssignedDeviceId());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        LicenseKeyResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getPoolId()).isEqualTo(entity.getPoolId());
        assertThat(response.getKeyValue()).isEqualTo(entity.getKeyValue());
        assertThat(response.getFeature()).isEqualTo(entity.getFeature());
        assertThat(response.getExpiresOn()).isEqualTo(entity.getExpiresOn());
        assertThat(response.getAssignedDeviceId()).isEqualTo(entity.getAssignedDeviceId());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        LicenseKeySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(LicenseKeyFixtures.entity())).hasSameSizeAs(LicenseKeyMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        LicenseKeyUpdateRequest request = new LicenseKeyUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setPoolId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setKeyValue("keyValue-3");
        request.setFeature("feature-3");
        request.setExpiresOn(LocalDate.of(2026, 4, 13));
        request.setAssignedDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(LicenseKeyLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getPoolId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getKeyValue()).isEqualTo("keyValue-3");
        assertThat(entity.getFeature()).isEqualTo("feature-3");
        assertThat(entity.getExpiresOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getAssignedDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(LicenseKeyLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        LicenseKey entity = LicenseKeyFixtures.entity();
        LicenseKey before = LicenseKeyFixtures.entity();
        mapper.applyUpdate(new LicenseKeyUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
