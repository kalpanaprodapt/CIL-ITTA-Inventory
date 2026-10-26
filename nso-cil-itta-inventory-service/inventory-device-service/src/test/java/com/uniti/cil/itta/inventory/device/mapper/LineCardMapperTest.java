package com.uniti.cil.itta.inventory.device.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import com.uniti.cil.itta.inventory.device.support.LineCardFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LineCardMapperTest {

    private final LineCardMapper mapper = new LineCardMapper();

    @Test
    void toEntityCopiesEveryField() {
        LineCardCreateRequest request = LineCardFixtures.createRequest();
        LineCard entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getChassisId()).isEqualTo(request.getChassisId());
        assertThat(entity.getSlotNumber()).isEqualTo(request.getSlotNumber());
        assertThat(entity.getCardType()).isEqualTo(request.getCardType());
        assertThat(entity.getPortCount()).isEqualTo(request.getPortCount());
        assertThat(entity.getSerialNumber()).isEqualTo(request.getSerialNumber());
        assertThat(entity.getFirmware()).isEqualTo(request.getFirmware());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        LineCard entity = LineCardFixtures.entity();
        LineCardResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getChassisId()).isEqualTo(entity.getChassisId());
        assertThat(response.getSlotNumber()).isEqualTo(entity.getSlotNumber());
        assertThat(response.getCardType()).isEqualTo(entity.getCardType());
        assertThat(response.getPortCount()).isEqualTo(entity.getPortCount());
        assertThat(response.getSerialNumber()).isEqualTo(entity.getSerialNumber());
        assertThat(response.getFirmware()).isEqualTo(entity.getFirmware());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        LineCard entity = LineCardFixtures.entity();
        LineCardSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(LineCardFixtures.entity())).hasSameSizeAs(LineCardMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        LineCard entity = LineCardFixtures.entity();
        LineCardUpdateRequest request = new LineCardUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setChassisId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSlotNumber(13);
        request.setCardType("cardType-3");
        request.setPortCount(13);
        request.setSerialNumber("serialNumber-3");
        request.setFirmware("firmware-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(LineCardCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getChassisId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSlotNumber()).isEqualTo(13);
        assertThat(entity.getCardType()).isEqualTo("cardType-3");
        assertThat(entity.getPortCount()).isEqualTo(13);
        assertThat(entity.getSerialNumber()).isEqualTo("serialNumber-3");
        assertThat(entity.getFirmware()).isEqualTo("firmware-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(LineCardCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        LineCard entity = LineCardFixtures.entity();
        LineCard before = LineCardFixtures.entity();
        mapper.applyUpdate(new LineCardUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
