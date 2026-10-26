package com.uniti.cil.itta.inventory.geography.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import com.uniti.cil.itta.inventory.geography.support.SiteFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SiteMapperTest {

    private final SiteMapper mapper = new SiteMapper();

    @Test
    void toEntityCopiesEveryField() {
        SiteCreateRequest request = SiteFixtures.createRequest();
        Site entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCityId()).isEqualTo(request.getCityId());
        assertThat(entity.getSiteType()).isEqualTo(request.getSiteType());
        assertThat(entity.getAddress()).isEqualTo(request.getAddress());
        assertThat(entity.getLatitude()).isEqualTo(request.getLatitude());
        assertThat(entity.getLongitude()).isEqualTo(request.getLongitude());
        assertThat(entity.getPowerKw()).isEqualTo(request.getPowerKw());
        assertThat(entity.getAccessHours()).isEqualTo(request.getAccessHours());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        Site entity = SiteFixtures.entity();
        SiteResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCityId()).isEqualTo(entity.getCityId());
        assertThat(response.getSiteType()).isEqualTo(entity.getSiteType());
        assertThat(response.getAddress()).isEqualTo(entity.getAddress());
        assertThat(response.getLatitude()).isEqualTo(entity.getLatitude());
        assertThat(response.getLongitude()).isEqualTo(entity.getLongitude());
        assertThat(response.getPowerKw()).isEqualTo(entity.getPowerKw());
        assertThat(response.getAccessHours()).isEqualTo(entity.getAccessHours());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        Site entity = SiteFixtures.entity();
        SiteSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SiteFixtures.entity())).hasSameSizeAs(SiteMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Site entity = SiteFixtures.entity();
        SiteUpdateRequest request = new SiteUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCityId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSiteType(SiteSiteType.DATACENTER);
        request.setAddress("address-3");
        request.setLatitude(new BigDecimal("15.50"));
        request.setLongitude(new BigDecimal("15.50"));
        request.setPowerKw(new BigDecimal("15.50"));
        request.setAccessHours("accessHours-3");
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(SiteLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCityId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSiteType()).isEqualTo(SiteSiteType.DATACENTER);
        assertThat(entity.getAddress()).isEqualTo("address-3");
        assertThat(entity.getLatitude()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getLongitude()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getPowerKw()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getAccessHours()).isEqualTo("accessHours-3");
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(SiteLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Site entity = SiteFixtures.entity();
        Site before = SiteFixtures.entity();
        mapper.applyUpdate(new SiteUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
