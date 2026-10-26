package com.uniti.cil.itta.inventory.change.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.change.dto.*;
import com.uniti.cil.itta.inventory.change.model.*;
import com.uniti.cil.itta.inventory.change.support.TechnicianFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TechnicianMapperTest {

    private final TechnicianMapper mapper = new TechnicianMapper();

    @Test
    void toEntityCopiesEveryField() {
        TechnicianCreateRequest request = TechnicianFixtures.createRequest();
        Technician entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getEmployeeId()).isEqualTo(request.getEmployeeId());
        assertThat(entity.getFullName()).isEqualTo(request.getFullName());
        assertThat(entity.getSkillSet()).isEqualTo(request.getSkillSet());
        assertThat(entity.getHomeSiteId()).isEqualTo(request.getHomeSiteId());
        assertThat(entity.getCertificationLevel()).isEqualTo(request.getCertificationLevel());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        Technician entity = TechnicianFixtures.entity();
        TechnicianResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getEmployeeId()).isEqualTo(entity.getEmployeeId());
        assertThat(response.getFullName()).isEqualTo(entity.getFullName());
        assertThat(response.getSkillSet()).isEqualTo(entity.getSkillSet());
        assertThat(response.getHomeSiteId()).isEqualTo(entity.getHomeSiteId());
        assertThat(response.getCertificationLevel()).isEqualTo(entity.getCertificationLevel());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        Technician entity = TechnicianFixtures.entity();
        TechnicianSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(TechnicianFixtures.entity())).hasSameSizeAs(TechnicianMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Technician entity = TechnicianFixtures.entity();
        TechnicianUpdateRequest request = new TechnicianUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setEmployeeId("employeeId-3");
        request.setFullName("fullName-3");
        request.setSkillSet("skillSet-3");
        request.setHomeSiteId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setCertificationLevel(13);
        request.setCriticality(TechnicianCriticality.HIGH);
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
        assertThat(entity.getEmployeeId()).isEqualTo("employeeId-3");
        assertThat(entity.getFullName()).isEqualTo("fullName-3");
        assertThat(entity.getSkillSet()).isEqualTo("skillSet-3");
        assertThat(entity.getHomeSiteId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getCertificationLevel()).isEqualTo(13);
        assertThat(entity.getCriticality()).isEqualTo(TechnicianCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Technician entity = TechnicianFixtures.entity();
        Technician before = TechnicianFixtures.entity();
        mapper.applyUpdate(new TechnicianUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
