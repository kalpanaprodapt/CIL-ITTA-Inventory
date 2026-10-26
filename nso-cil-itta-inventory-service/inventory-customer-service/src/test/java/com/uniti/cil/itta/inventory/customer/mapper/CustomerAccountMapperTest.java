package com.uniti.cil.itta.inventory.customer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import com.uniti.cil.itta.inventory.customer.support.CustomerAccountFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerAccountMapperTest {

    private final CustomerAccountMapper mapper = new CustomerAccountMapper();

    @Test
    void toEntityCopiesEveryField() {
        CustomerAccountCreateRequest request = CustomerAccountFixtures.createRequest();
        CustomerAccount entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(entity.getAccountNumber()).isEqualTo(request.getAccountNumber());
        assertThat(entity.getBillingCycle()).isEqualTo(request.getBillingCycle());
        assertThat(entity.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(entity.getCreditLimit()).isEqualTo(request.getCreditLimit());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        CustomerAccountResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCustomerId()).isEqualTo(entity.getCustomerId());
        assertThat(response.getAccountNumber()).isEqualTo(entity.getAccountNumber());
        assertThat(response.getBillingCycle()).isEqualTo(entity.getBillingCycle());
        assertThat(response.getCurrency()).isEqualTo(entity.getCurrency());
        assertThat(response.getCreditLimit()).isEqualTo(entity.getCreditLimit());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        CustomerAccountSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CustomerAccountFixtures.entity())).hasSameSizeAs(CustomerAccountMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        CustomerAccountUpdateRequest request = new CustomerAccountUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setAccountNumber("accountNumber-3");
        request.setBillingCycle(CustomerAccountBillingCycle.ANNUAL);
        request.setCurrency("currency-3");
        request.setCreditLimit(new BigDecimal("15.50"));
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(CustomerAccountLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCustomerId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getAccountNumber()).isEqualTo("accountNumber-3");
        assertThat(entity.getBillingCycle()).isEqualTo(CustomerAccountBillingCycle.ANNUAL);
        assertThat(entity.getCurrency()).isEqualTo("currency-3");
        assertThat(entity.getCreditLimit()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(CustomerAccountLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        CustomerAccount entity = CustomerAccountFixtures.entity();
        CustomerAccount before = CustomerAccountFixtures.entity();
        mapper.applyUpdate(new CustomerAccountUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
