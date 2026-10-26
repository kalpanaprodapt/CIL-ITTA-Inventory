package com.uniti.cil.itta.inventory.facility.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.facility.dto.*;
import com.uniti.cil.itta.inventory.facility.model.*;
import com.uniti.cil.itta.inventory.facility.support.RackUnitFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RackUnitMapperTest {

    private final RackUnitMapper mapper = new RackUnitMapper();

    @Test
    void toEntityCopiesEveryField() {
        RackUnitCreateRequest request = RackUnitFixtures.createRequest();
        RackUnit entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getRackId()).isEqualTo(request.getRackId());
        assertThat(entity.getUnitNumber()).isEqualTo(request.getUnitNumber());
        assertThat(entity.getOccupied()).isEqualTo(request.getOccupied());
        assertThat(entity.getMountedDeviceId()).isEqualTo(request.getMountedDeviceId());
        assertThat(entity.getFace()).isEqualTo(request.getFace());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        RackUnit entity = RackUnitFixtures.entity();
        RackUnitResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getRackId()).isEqualTo(entity.getRackId());
        assertThat(response.getUnitNumber()).isEqualTo(entity.getUnitNumber());
        assertThat(response.getOccupied()).isEqualTo(entity.getOccupied());
        assertThat(response.getMountedDeviceId()).isEqualTo(entity.getMountedDeviceId());
        assertThat(response.getFace()).isEqualTo(entity.getFace());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        RackUnit entity = RackUnitFixtures.entity();
        RackUnitSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(RackUnitFixtures.entity())).hasSameSizeAs(RackUnitMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        RackUnit entity = RackUnitFixtures.entity();
        RackUnitUpdateRequest request = new RackUnitUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setRackId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setUnitNumber(13);
        request.setOccupied(Boolean.TRUE);
        request.setMountedDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setFace(RackUnitFace.FRONT);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(RackUnitCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getRackId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getUnitNumber()).isEqualTo(13);
        assertThat(entity.getOccupied()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getMountedDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getFace()).isEqualTo(RackUnitFace.FRONT);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(RackUnitCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        RackUnit entity = RackUnitFixtures.entity();
        RackUnit before = RackUnitFixtures.entity();
        mapper.applyUpdate(new RackUnitUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
