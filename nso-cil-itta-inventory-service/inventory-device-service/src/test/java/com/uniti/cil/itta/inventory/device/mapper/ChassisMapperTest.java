package com.uniti.cil.itta.inventory.device.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import com.uniti.cil.itta.inventory.device.support.ChassisFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChassisMapperTest {

    private final ChassisMapper mapper = new ChassisMapper();

    @Test
    void toEntityCopiesEveryField() {
        ChassisCreateRequest request = ChassisFixtures.createRequest();
        Chassis entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getDeviceId()).isEqualTo(request.getDeviceId());
        assertThat(entity.getSlotCount()).isEqualTo(request.getSlotCount());
        assertThat(entity.getSerialNumber()).isEqualTo(request.getSerialNumber());
        assertThat(entity.getBackplaneGbps()).isEqualTo(request.getBackplaneGbps());
        assertThat(entity.getRedundancyMode()).isEqualTo(request.getRedundancyMode());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        Chassis entity = ChassisFixtures.entity();
        ChassisResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getDeviceId()).isEqualTo(entity.getDeviceId());
        assertThat(response.getSlotCount()).isEqualTo(entity.getSlotCount());
        assertThat(response.getSerialNumber()).isEqualTo(entity.getSerialNumber());
        assertThat(response.getBackplaneGbps()).isEqualTo(entity.getBackplaneGbps());
        assertThat(response.getRedundancyMode()).isEqualTo(entity.getRedundancyMode());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        Chassis entity = ChassisFixtures.entity();
        ChassisSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ChassisFixtures.entity())).hasSameSizeAs(ChassisMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Chassis entity = ChassisFixtures.entity();
        ChassisUpdateRequest request = new ChassisUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSlotCount(13);
        request.setSerialNumber("serialNumber-3");
        request.setBackplaneGbps(new BigDecimal("15.50"));
        request.setRedundancyMode("redundancyMode-3");
        request.setLifecycleStage(ChassisLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(ChassisCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSlotCount()).isEqualTo(13);
        assertThat(entity.getSerialNumber()).isEqualTo("serialNumber-3");
        assertThat(entity.getBackplaneGbps()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getRedundancyMode()).isEqualTo("redundancyMode-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(ChassisLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(ChassisCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Chassis entity = ChassisFixtures.entity();
        Chassis before = ChassisFixtures.entity();
        mapper.applyUpdate(new ChassisUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
