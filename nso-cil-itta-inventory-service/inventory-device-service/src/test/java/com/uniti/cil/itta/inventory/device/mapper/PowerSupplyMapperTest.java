package com.uniti.cil.itta.inventory.device.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import com.uniti.cil.itta.inventory.device.support.PowerSupplyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PowerSupplyMapperTest {

    private final PowerSupplyMapper mapper = new PowerSupplyMapper();

    @Test
    void toEntityCopiesEveryField() {
        PowerSupplyCreateRequest request = PowerSupplyFixtures.createRequest();
        PowerSupply entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getChassisId()).isEqualTo(request.getChassisId());
        assertThat(entity.getSlotNumber()).isEqualTo(request.getSlotNumber());
        assertThat(entity.getWattage()).isEqualTo(request.getWattage());
        assertThat(entity.getFeedId()).isEqualTo(request.getFeedId());
        assertThat(entity.getSerialNumber()).isEqualTo(request.getSerialNumber());
        assertThat(entity.getEfficiency()).isEqualTo(request.getEfficiency());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        PowerSupplyResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getChassisId()).isEqualTo(entity.getChassisId());
        assertThat(response.getSlotNumber()).isEqualTo(entity.getSlotNumber());
        assertThat(response.getWattage()).isEqualTo(entity.getWattage());
        assertThat(response.getFeedId()).isEqualTo(entity.getFeedId());
        assertThat(response.getSerialNumber()).isEqualTo(entity.getSerialNumber());
        assertThat(response.getEfficiency()).isEqualTo(entity.getEfficiency());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        PowerSupplySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PowerSupplyFixtures.entity())).hasSameSizeAs(PowerSupplyMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        PowerSupplyUpdateRequest request = new PowerSupplyUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSlotNumber(13);
        request.setWattage(13);
        request.setFeedId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSerialNumber("serialNumber-3");
        request.setEfficiency(new BigDecimal("15.50"));
        request.setCriticality(PowerSupplyCriticality.HIGH);
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
        assertThat(entity.getChassisId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSlotNumber()).isEqualTo(13);
        assertThat(entity.getWattage()).isEqualTo(13);
        assertThat(entity.getFeedId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSerialNumber()).isEqualTo("serialNumber-3");
        assertThat(entity.getEfficiency()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCriticality()).isEqualTo(PowerSupplyCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        PowerSupply before = PowerSupplyFixtures.entity();
        mapper.applyUpdate(new PowerSupplyUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
