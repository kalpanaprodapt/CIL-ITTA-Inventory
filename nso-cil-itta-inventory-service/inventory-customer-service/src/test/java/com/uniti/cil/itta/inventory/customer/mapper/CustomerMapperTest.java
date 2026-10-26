package com.uniti.cil.itta.inventory.customer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import com.uniti.cil.itta.inventory.customer.support.CustomerFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerMapperTest {

    private final CustomerMapper mapper = new CustomerMapper();

    @Test
    void toEntityCopiesEveryField() {
        CustomerCreateRequest request = CustomerFixtures.createRequest();
        Customer entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getLegalName()).isEqualTo(request.getLegalName());
        assertThat(entity.getSegment()).isEqualTo(request.getSegment());
        assertThat(entity.getTaxId()).isEqualTo(request.getTaxId());
        assertThat(entity.getIndustry()).isEqualTo(request.getIndustry());
        assertThat(entity.getAccountManager()).isEqualTo(request.getAccountManager());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        Customer entity = CustomerFixtures.entity();
        CustomerResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getLegalName()).isEqualTo(entity.getLegalName());
        assertThat(response.getSegment()).isEqualTo(entity.getSegment());
        assertThat(response.getTaxId()).isEqualTo(entity.getTaxId());
        assertThat(response.getIndustry()).isEqualTo(entity.getIndustry());
        assertThat(response.getAccountManager()).isEqualTo(entity.getAccountManager());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        Customer entity = CustomerFixtures.entity();
        CustomerSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CustomerFixtures.entity())).hasSameSizeAs(CustomerMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Customer entity = CustomerFixtures.entity();
        CustomerUpdateRequest request = new CustomerUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setLegalName("legalName-3");
        request.setSegment(CustomerSegment.GOVERNMENT);
        request.setTaxId("taxId-3");
        request.setIndustry("industry-3");
        request.setAccountManager("accountManager-3");
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getLegalName()).isEqualTo("legalName-3");
        assertThat(entity.getSegment()).isEqualTo(CustomerSegment.GOVERNMENT);
        assertThat(entity.getTaxId()).isEqualTo("taxId-3");
        assertThat(entity.getIndustry()).isEqualTo("industry-3");
        assertThat(entity.getAccountManager()).isEqualTo("accountManager-3");
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Customer entity = CustomerFixtures.entity();
        Customer before = CustomerFixtures.entity();
        mapper.applyUpdate(new CustomerUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
