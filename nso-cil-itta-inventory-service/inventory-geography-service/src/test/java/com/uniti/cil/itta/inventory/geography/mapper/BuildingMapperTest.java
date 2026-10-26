package com.uniti.cil.itta.inventory.geography.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import com.uniti.cil.itta.inventory.geography.support.BuildingFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BuildingMapperTest {

    private final BuildingMapper mapper = new BuildingMapper();

    @Test
    void toEntityCopiesEveryField() {
        BuildingCreateRequest request = BuildingFixtures.createRequest();
        Building entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSiteId()).isEqualTo(request.getSiteId());
        assertThat(entity.getFloors()).isEqualTo(request.getFloors());
        assertThat(entity.getYearBuilt()).isEqualTo(request.getYearBuilt());
        assertThat(entity.getOwnership()).isEqualTo(request.getOwnership());
        assertThat(entity.getLeaseExpiry()).isEqualTo(request.getLeaseExpiry());
        assertThat(entity.getSecurityLevel()).isEqualTo(request.getSecurityLevel());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        Building entity = BuildingFixtures.entity();
        BuildingResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSiteId()).isEqualTo(entity.getSiteId());
        assertThat(response.getFloors()).isEqualTo(entity.getFloors());
        assertThat(response.getYearBuilt()).isEqualTo(entity.getYearBuilt());
        assertThat(response.getOwnership()).isEqualTo(entity.getOwnership());
        assertThat(response.getLeaseExpiry()).isEqualTo(entity.getLeaseExpiry());
        assertThat(response.getSecurityLevel()).isEqualTo(entity.getSecurityLevel());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        Building entity = BuildingFixtures.entity();
        BuildingSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(BuildingFixtures.entity())).hasSameSizeAs(BuildingMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Building entity = BuildingFixtures.entity();
        BuildingUpdateRequest request = new BuildingUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setFloors(13);
        request.setYearBuilt(13);
        request.setOwnership(BuildingOwnership.COLO);
        request.setLeaseExpiry(LocalDate.of(2026, 4, 13));
        request.setSecurityLevel(13);
        request.setLifecycleStage(BuildingLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(BuildingCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSiteId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getFloors()).isEqualTo(13);
        assertThat(entity.getYearBuilt()).isEqualTo(13);
        assertThat(entity.getOwnership()).isEqualTo(BuildingOwnership.COLO);
        assertThat(entity.getLeaseExpiry()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getSecurityLevel()).isEqualTo(13);
        assertThat(entity.getLifecycleStage()).isEqualTo(BuildingLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(BuildingCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Building entity = BuildingFixtures.entity();
        Building before = BuildingFixtures.entity();
        mapper.applyUpdate(new BuildingUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
