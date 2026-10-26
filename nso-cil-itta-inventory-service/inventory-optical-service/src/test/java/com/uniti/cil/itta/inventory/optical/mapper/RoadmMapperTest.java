package com.uniti.cil.itta.inventory.optical.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import com.uniti.cil.itta.inventory.optical.support.RoadmFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoadmMapperTest {

    private final RoadmMapper mapper = new RoadmMapper();

    @Test
    void toEntityCopiesEveryField() {
        RoadmCreateRequest request = RoadmFixtures.createRequest();
        Roadm entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSystemId()).isEqualTo(request.getSystemId());
        assertThat(entity.getDegrees()).isEqualTo(request.getDegrees());
        assertThat(entity.getColorless()).isEqualTo(request.getColorless());
        assertThat(entity.getDirectionless()).isEqualTo(request.getDirectionless());
        assertThat(entity.getContentionless()).isEqualTo(request.getContentionless());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        Roadm entity = RoadmFixtures.entity();
        RoadmResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSystemId()).isEqualTo(entity.getSystemId());
        assertThat(response.getDegrees()).isEqualTo(entity.getDegrees());
        assertThat(response.getColorless()).isEqualTo(entity.getColorless());
        assertThat(response.getDirectionless()).isEqualTo(entity.getDirectionless());
        assertThat(response.getContentionless()).isEqualTo(entity.getContentionless());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        Roadm entity = RoadmFixtures.entity();
        RoadmSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(RoadmFixtures.entity())).hasSameSizeAs(RoadmMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Roadm entity = RoadmFixtures.entity();
        RoadmUpdateRequest request = new RoadmUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSystemId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setDegrees(13);
        request.setColorless(Boolean.TRUE);
        request.setDirectionless(Boolean.TRUE);
        request.setContentionless(Boolean.TRUE);
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(RoadmCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSystemId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getDegrees()).isEqualTo(13);
        assertThat(entity.getColorless()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getDirectionless()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getContentionless()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(RoadmCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Roadm entity = RoadmFixtures.entity();
        Roadm before = RoadmFixtures.entity();
        mapper.applyUpdate(new RoadmUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
