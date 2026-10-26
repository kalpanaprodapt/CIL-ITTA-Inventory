package com.uniti.cil.itta.inventory.cabling.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.*;
import com.uniti.cil.itta.inventory.cabling.support.FiberStrandFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FiberStrandMapperTest {

    private final FiberStrandMapper mapper = new FiberStrandMapper();

    @Test
    void toEntityCopiesEveryField() {
        FiberStrandCreateRequest request = FiberStrandFixtures.createRequest();
        FiberStrand entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCableId()).isEqualTo(request.getCableId());
        assertThat(entity.getStrandNumber()).isEqualTo(request.getStrandNumber());
        assertThat(entity.getBufferTube()).isEqualTo(request.getBufferTube());
        assertThat(entity.getTubeColor()).isEqualTo(request.getTubeColor());
        assertThat(entity.getAttenuationDb()).isEqualTo(request.getAttenuationDb());
        assertThat(entity.getInUse()).isEqualTo(request.getInUse());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        FiberStrand entity = FiberStrandFixtures.entity();
        FiberStrandResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCableId()).isEqualTo(entity.getCableId());
        assertThat(response.getStrandNumber()).isEqualTo(entity.getStrandNumber());
        assertThat(response.getBufferTube()).isEqualTo(entity.getBufferTube());
        assertThat(response.getTubeColor()).isEqualTo(entity.getTubeColor());
        assertThat(response.getAttenuationDb()).isEqualTo(entity.getAttenuationDb());
        assertThat(response.getInUse()).isEqualTo(entity.getInUse());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        FiberStrand entity = FiberStrandFixtures.entity();
        FiberStrandSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(FiberStrandFixtures.entity())).hasSameSizeAs(FiberStrandMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        FiberStrand entity = FiberStrandFixtures.entity();
        FiberStrandUpdateRequest request = new FiberStrandUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCableId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setStrandNumber(13);
        request.setBufferTube("bufferTube-3");
        request.setTubeColor("tubeColor-3");
        request.setAttenuationDb(new BigDecimal("15.50"));
        request.setInUse(Boolean.TRUE);
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(FiberStrandCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCableId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getStrandNumber()).isEqualTo(13);
        assertThat(entity.getBufferTube()).isEqualTo("bufferTube-3");
        assertThat(entity.getTubeColor()).isEqualTo("tubeColor-3");
        assertThat(entity.getAttenuationDb()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getInUse()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(FiberStrandCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        FiberStrand entity = FiberStrandFixtures.entity();
        FiberStrand before = FiberStrandFixtures.entity();
        mapper.applyUpdate(new FiberStrandUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
