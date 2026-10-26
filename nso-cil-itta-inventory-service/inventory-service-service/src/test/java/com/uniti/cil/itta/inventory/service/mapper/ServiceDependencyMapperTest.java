package com.uniti.cil.itta.inventory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import com.uniti.cil.itta.inventory.service.support.ServiceDependencyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceDependencyMapperTest {

    private final ServiceDependencyMapper mapper = new ServiceDependencyMapper();

    @Test
    void toEntityCopiesEveryField() {
        ServiceDependencyCreateRequest request = ServiceDependencyFixtures.createRequest();
        ServiceDependency entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getServiceId()).isEqualTo(request.getServiceId());
        assertThat(entity.getDependsOnId()).isEqualTo(request.getDependsOnId());
        assertThat(entity.getDependencyType()).isEqualTo(request.getDependencyType());
        assertThat(entity.getImpactScore()).isEqualTo(request.getImpactScore());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        ServiceDependencyResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getServiceId()).isEqualTo(entity.getServiceId());
        assertThat(response.getDependsOnId()).isEqualTo(entity.getDependsOnId());
        assertThat(response.getDependencyType()).isEqualTo(entity.getDependencyType());
        assertThat(response.getImpactScore()).isEqualTo(entity.getImpactScore());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        ServiceDependencySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ServiceDependencyFixtures.entity())).hasSameSizeAs(ServiceDependencyMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        ServiceDependencyUpdateRequest request = new ServiceDependencyUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setServiceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setDependsOnId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setDependencyType(ServiceDependencyDependencyType.OPTIONAL);
        request.setImpactScore(new BigDecimal("15.50"));
        request.setCriticality(ServiceDependencyCriticality.HIGH);
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
        assertThat(entity.getServiceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getDependsOnId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getDependencyType()).isEqualTo(ServiceDependencyDependencyType.OPTIONAL);
        assertThat(entity.getImpactScore()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCriticality()).isEqualTo(ServiceDependencyCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ServiceDependency entity = ServiceDependencyFixtures.entity();
        ServiceDependency before = ServiceDependencyFixtures.entity();
        mapper.applyUpdate(new ServiceDependencyUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
