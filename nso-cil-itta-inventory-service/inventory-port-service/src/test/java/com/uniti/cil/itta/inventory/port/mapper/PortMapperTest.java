package com.uniti.cil.itta.inventory.port.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import com.uniti.cil.itta.inventory.port.support.PortFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PortMapperTest {

    private final PortMapper mapper = new PortMapper();

    @Test
    void toEntityCopiesEveryField() {
        PortCreateRequest request = PortFixtures.createRequest();
        Port entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getDeviceId()).isEqualTo(request.getDeviceId());
        assertThat(entity.getCardId()).isEqualTo(request.getCardId());
        assertThat(entity.getPortNumber()).isEqualTo(request.getPortNumber());
        assertThat(entity.getSpeedMbps()).isEqualTo(request.getSpeedMbps());
        assertThat(entity.getMediaType()).isEqualTo(request.getMediaType());
        assertThat(entity.getAdminState()).isEqualTo(request.getAdminState());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        Port entity = PortFixtures.entity();
        PortResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getDeviceId()).isEqualTo(entity.getDeviceId());
        assertThat(response.getCardId()).isEqualTo(entity.getCardId());
        assertThat(response.getPortNumber()).isEqualTo(entity.getPortNumber());
        assertThat(response.getSpeedMbps()).isEqualTo(entity.getSpeedMbps());
        assertThat(response.getMediaType()).isEqualTo(entity.getMediaType());
        assertThat(response.getAdminState()).isEqualTo(entity.getAdminState());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        Port entity = PortFixtures.entity();
        PortSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PortFixtures.entity())).hasSameSizeAs(PortMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Port entity = PortFixtures.entity();
        PortUpdateRequest request = new PortUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setCardId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setPortNumber("portNumber-3");
        request.setSpeedMbps(1003L);
        request.setMediaType(PortMediaType.DAC);
        request.setAdminState(PortAdminState.UP);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(PortCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getCardId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getPortNumber()).isEqualTo("portNumber-3");
        assertThat(entity.getSpeedMbps()).isEqualTo(1003L);
        assertThat(entity.getMediaType()).isEqualTo(PortMediaType.DAC);
        assertThat(entity.getAdminState()).isEqualTo(PortAdminState.UP);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(PortCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Port entity = PortFixtures.entity();
        Port before = PortFixtures.entity();
        mapper.applyUpdate(new PortUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
