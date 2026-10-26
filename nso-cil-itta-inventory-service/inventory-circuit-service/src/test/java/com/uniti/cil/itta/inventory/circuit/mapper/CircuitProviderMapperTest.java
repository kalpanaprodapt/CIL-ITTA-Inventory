package com.uniti.cil.itta.inventory.circuit.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.circuit.dto.*;
import com.uniti.cil.itta.inventory.circuit.model.*;
import com.uniti.cil.itta.inventory.circuit.support.CircuitProviderFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitProviderMapperTest {

    private final CircuitProviderMapper mapper = new CircuitProviderMapper();

    @Test
    void toEntityCopiesEveryField() {
        CircuitProviderCreateRequest request = CircuitProviderFixtures.createRequest();
        CircuitProvider entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getProviderName()).isEqualTo(request.getProviderName());
        assertThat(entity.getAccountNumber()).isEqualTo(request.getAccountNumber());
        assertThat(entity.getNoc()).isEqualTo(request.getNoc());
        assertThat(entity.getEscalationEmail()).isEqualTo(request.getEscalationEmail());
        assertThat(entity.getProviderType()).isEqualTo(request.getProviderType());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        CircuitProviderResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getProviderName()).isEqualTo(entity.getProviderName());
        assertThat(response.getAccountNumber()).isEqualTo(entity.getAccountNumber());
        assertThat(response.getNoc()).isEqualTo(entity.getNoc());
        assertThat(response.getEscalationEmail()).isEqualTo(entity.getEscalationEmail());
        assertThat(response.getProviderType()).isEqualTo(entity.getProviderType());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        CircuitProviderSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CircuitProviderFixtures.entity())).hasSameSizeAs(CircuitProviderMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        CircuitProviderUpdateRequest request = new CircuitProviderUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setProviderName("providerName-3");
        request.setAccountNumber("accountNumber-3");
        request.setNoc("noc-3");
        request.setEscalationEmail("escalationEmail-3");
        request.setProviderType(CircuitProviderProviderType.LOCAL);
        request.setOwner("owner-3");
        request.setCriticality(CircuitProviderCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getProviderName()).isEqualTo("providerName-3");
        assertThat(entity.getAccountNumber()).isEqualTo("accountNumber-3");
        assertThat(entity.getNoc()).isEqualTo("noc-3");
        assertThat(entity.getEscalationEmail()).isEqualTo("escalationEmail-3");
        assertThat(entity.getProviderType()).isEqualTo(CircuitProviderProviderType.LOCAL);
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(CircuitProviderCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        CircuitProvider before = CircuitProviderFixtures.entity();
        mapper.applyUpdate(new CircuitProviderUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
