package com.uniti.cil.itta.inventory.customer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import com.uniti.cil.itta.inventory.customer.support.ContractFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ContractMapperTest {

    private final ContractMapper mapper = new ContractMapper();

    @Test
    void toEntityCopiesEveryField() {
        ContractCreateRequest request = ContractFixtures.createRequest();
        Contract entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(entity.getContractNumber()).isEqualTo(request.getContractNumber());
        assertThat(entity.getStartDate()).isEqualTo(request.getStartDate());
        assertThat(entity.getEndDate()).isEqualTo(request.getEndDate());
        assertThat(entity.getTermMonths()).isEqualTo(request.getTermMonths());
        assertThat(entity.getAutoRenew()).isEqualTo(request.getAutoRenew());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        Contract entity = ContractFixtures.entity();
        ContractResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCustomerId()).isEqualTo(entity.getCustomerId());
        assertThat(response.getContractNumber()).isEqualTo(entity.getContractNumber());
        assertThat(response.getStartDate()).isEqualTo(entity.getStartDate());
        assertThat(response.getEndDate()).isEqualTo(entity.getEndDate());
        assertThat(response.getTermMonths()).isEqualTo(entity.getTermMonths());
        assertThat(response.getAutoRenew()).isEqualTo(entity.getAutoRenew());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        Contract entity = ContractFixtures.entity();
        ContractSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ContractFixtures.entity())).hasSameSizeAs(ContractMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Contract entity = ContractFixtures.entity();
        ContractUpdateRequest request = new ContractUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setContractNumber("contractNumber-3");
        request.setStartDate(LocalDate.of(2026, 4, 13));
        request.setEndDate(LocalDate.of(2026, 4, 13));
        request.setTermMonths(13);
        request.setAutoRenew(Boolean.TRUE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(ContractCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCustomerId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getContractNumber()).isEqualTo("contractNumber-3");
        assertThat(entity.getStartDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEndDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getTermMonths()).isEqualTo(13);
        assertThat(entity.getAutoRenew()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(ContractCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Contract entity = ContractFixtures.entity();
        Contract before = ContractFixtures.entity();
        mapper.applyUpdate(new ContractUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
