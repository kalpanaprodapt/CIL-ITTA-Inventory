package com.uniti.cil.itta.inventory.vendor.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import com.uniti.cil.itta.inventory.vendor.support.SupportContractFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SupportContractMapperTest {

    private final SupportContractMapper mapper = new SupportContractMapper();

    @Test
    void toEntityCopiesEveryField() {
        SupportContractCreateRequest request = SupportContractFixtures.createRequest();
        SupportContract entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVendorId()).isEqualTo(request.getVendorId());
        assertThat(entity.getContractNumber()).isEqualTo(request.getContractNumber());
        assertThat(entity.getStartDate()).isEqualTo(request.getStartDate());
        assertThat(entity.getEndDate()).isEqualTo(request.getEndDate());
        assertThat(entity.getCoverageLevel()).isEqualTo(request.getCoverageLevel());
        assertThat(entity.getAnnualCost()).isEqualTo(request.getAnnualCost());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        SupportContract entity = SupportContractFixtures.entity();
        SupportContractResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVendorId()).isEqualTo(entity.getVendorId());
        assertThat(response.getContractNumber()).isEqualTo(entity.getContractNumber());
        assertThat(response.getStartDate()).isEqualTo(entity.getStartDate());
        assertThat(response.getEndDate()).isEqualTo(entity.getEndDate());
        assertThat(response.getCoverageLevel()).isEqualTo(entity.getCoverageLevel());
        assertThat(response.getAnnualCost()).isEqualTo(entity.getAnnualCost());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        SupportContract entity = SupportContractFixtures.entity();
        SupportContractSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SupportContractFixtures.entity())).hasSameSizeAs(SupportContractMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        SupportContract entity = SupportContractFixtures.entity();
        SupportContractUpdateRequest request = new SupportContractUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setContractNumber("contractNumber-3");
        request.setStartDate(LocalDate.of(2026, 4, 13));
        request.setEndDate(LocalDate.of(2026, 4, 13));
        request.setCoverageLevel(SupportContractCoverageLevel.GOLD);
        request.setAnnualCost(new BigDecimal("15.50"));
        request.setOwner("owner-3");
        request.setCriticality(SupportContractCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVendorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getContractNumber()).isEqualTo("contractNumber-3");
        assertThat(entity.getStartDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEndDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getCoverageLevel()).isEqualTo(SupportContractCoverageLevel.GOLD);
        assertThat(entity.getAnnualCost()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SupportContractCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        SupportContract entity = SupportContractFixtures.entity();
        SupportContract before = SupportContractFixtures.entity();
        mapper.applyUpdate(new SupportContractUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
