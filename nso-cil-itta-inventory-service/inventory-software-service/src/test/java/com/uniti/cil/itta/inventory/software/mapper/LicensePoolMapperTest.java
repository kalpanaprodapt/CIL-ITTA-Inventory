package com.uniti.cil.itta.inventory.software.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.software.dto.*;
import com.uniti.cil.itta.inventory.software.model.*;
import com.uniti.cil.itta.inventory.software.support.LicensePoolFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LicensePoolMapperTest {

    private final LicensePoolMapper mapper = new LicensePoolMapper();

    @Test
    void toEntityCopiesEveryField() {
        LicensePoolCreateRequest request = LicensePoolFixtures.createRequest();
        LicensePool entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVendorId()).isEqualTo(request.getVendorId());
        assertThat(entity.getPoolName()).isEqualTo(request.getPoolName());
        assertThat(entity.getTotalSeats()).isEqualTo(request.getTotalSeats());
        assertThat(entity.getUsedSeats()).isEqualTo(request.getUsedSeats());
        assertThat(entity.getRenewalDate()).isEqualTo(request.getRenewalDate());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        LicensePool entity = LicensePoolFixtures.entity();
        LicensePoolResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVendorId()).isEqualTo(entity.getVendorId());
        assertThat(response.getPoolName()).isEqualTo(entity.getPoolName());
        assertThat(response.getTotalSeats()).isEqualTo(entity.getTotalSeats());
        assertThat(response.getUsedSeats()).isEqualTo(entity.getUsedSeats());
        assertThat(response.getRenewalDate()).isEqualTo(entity.getRenewalDate());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        LicensePool entity = LicensePoolFixtures.entity();
        LicensePoolSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(LicensePoolFixtures.entity())).hasSameSizeAs(LicensePoolMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        LicensePool entity = LicensePoolFixtures.entity();
        LicensePoolUpdateRequest request = new LicensePoolUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setPoolName("poolName-3");
        request.setTotalSeats(13);
        request.setUsedSeats(13);
        request.setRenewalDate(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(LicensePoolLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVendorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getPoolName()).isEqualTo("poolName-3");
        assertThat(entity.getTotalSeats()).isEqualTo(13);
        assertThat(entity.getUsedSeats()).isEqualTo(13);
        assertThat(entity.getRenewalDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(LicensePoolLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        LicensePool entity = LicensePoolFixtures.entity();
        LicensePool before = LicensePoolFixtures.entity();
        mapper.applyUpdate(new LicensePoolUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
