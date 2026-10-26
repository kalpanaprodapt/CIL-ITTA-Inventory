package com.uniti.cil.itta.inventory.vendor.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import com.uniti.cil.itta.inventory.vendor.support.WarrantyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WarrantyMapperTest {

    private final WarrantyMapper mapper = new WarrantyMapper();

    @Test
    void toEntityCopiesEveryField() {
        WarrantyCreateRequest request = WarrantyFixtures.createRequest();
        Warranty entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAssetId()).isEqualTo(request.getAssetId());
        assertThat(entity.getStartDate()).isEqualTo(request.getStartDate());
        assertThat(entity.getEndDate()).isEqualTo(request.getEndDate());
        assertThat(entity.getWarrantyType()).isEqualTo(request.getWarrantyType());
        assertThat(entity.getTerms()).isEqualTo(request.getTerms());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        Warranty entity = WarrantyFixtures.entity();
        WarrantyResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAssetId()).isEqualTo(entity.getAssetId());
        assertThat(response.getStartDate()).isEqualTo(entity.getStartDate());
        assertThat(response.getEndDate()).isEqualTo(entity.getEndDate());
        assertThat(response.getWarrantyType()).isEqualTo(entity.getWarrantyType());
        assertThat(response.getTerms()).isEqualTo(entity.getTerms());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        Warranty entity = WarrantyFixtures.entity();
        WarrantySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(WarrantyFixtures.entity())).hasSameSizeAs(WarrantyMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Warranty entity = WarrantyFixtures.entity();
        WarrantyUpdateRequest request = new WarrantyUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAssetId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setStartDate(LocalDate.of(2026, 4, 13));
        request.setEndDate(LocalDate.of(2026, 4, 13));
        request.setWarrantyType(WarrantyWarrantyType.ADVANCED_REPLACEMENT);
        request.setTerms("terms-3");
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
        assertThat(entity.getAssetId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getStartDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEndDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getWarrantyType()).isEqualTo(WarrantyWarrantyType.ADVANCED_REPLACEMENT);
        assertThat(entity.getTerms()).isEqualTo("terms-3");
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Warranty entity = WarrantyFixtures.entity();
        Warranty before = WarrantyFixtures.entity();
        mapper.applyUpdate(new WarrantyUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
