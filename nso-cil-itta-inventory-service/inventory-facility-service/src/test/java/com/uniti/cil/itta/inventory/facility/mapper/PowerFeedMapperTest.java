package com.uniti.cil.itta.inventory.facility.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import com.uniti.cil.itta.inventory.facility.support.PowerFeedFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PowerFeedMapperTest {

    private final PowerFeedMapper mapper = new PowerFeedMapper();

    @Test
    void toEntityCopiesEveryField() {
        PowerFeedCreateRequest request = PowerFeedFixtures.createRequest();
        PowerFeed entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRoomId()).isEqualTo(request.getRoomId());
        assertThat(entity.getFeedType()).isEqualTo(request.getFeedType());
        assertThat(entity.getVoltage()).isEqualTo(request.getVoltage());
        assertThat(entity.getAmperage()).isEqualTo(request.getAmperage());
        assertThat(entity.getPhase()).isEqualTo(request.getPhase());
        assertThat(entity.getBreakerLabel()).isEqualTo(request.getBreakerLabel());
        assertThat(entity.getRedundancyGroup()).isEqualTo(request.getRedundancyGroup());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        PowerFeed entity = PowerFeedFixtures.entity();
        PowerFeedResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRoomId()).isEqualTo(entity.getRoomId());
        assertThat(response.getFeedType()).isEqualTo(entity.getFeedType());
        assertThat(response.getVoltage()).isEqualTo(entity.getVoltage());
        assertThat(response.getAmperage()).isEqualTo(entity.getAmperage());
        assertThat(response.getPhase()).isEqualTo(entity.getPhase());
        assertThat(response.getBreakerLabel()).isEqualTo(entity.getBreakerLabel());
        assertThat(response.getRedundancyGroup()).isEqualTo(entity.getRedundancyGroup());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        PowerFeed entity = PowerFeedFixtures.entity();
        PowerFeedSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PowerFeedFixtures.entity())).hasSameSizeAs(PowerFeedMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        PowerFeed entity = PowerFeedFixtures.entity();
        PowerFeedUpdateRequest request = new PowerFeedUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRoomId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setFeedType(PowerFeedFeedType.AC);
        request.setVoltage(new BigDecimal("15.50"));
        request.setAmperage(new BigDecimal("15.50"));
        request.setPhase("phase-3");
        request.setBreakerLabel("breakerLabel-3");
        request.setRedundancyGroup("redundancyGroup-3");
        request.setOwner("owner-3");
        request.setCriticality(PowerFeedCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRoomId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getFeedType()).isEqualTo(PowerFeedFeedType.AC);
        assertThat(entity.getVoltage()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getAmperage()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getPhase()).isEqualTo("phase-3");
        assertThat(entity.getBreakerLabel()).isEqualTo("breakerLabel-3");
        assertThat(entity.getRedundancyGroup()).isEqualTo("redundancyGroup-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(PowerFeedCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        PowerFeed entity = PowerFeedFixtures.entity();
        PowerFeed before = PowerFeedFixtures.entity();
        mapper.applyUpdate(new PowerFeedUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
