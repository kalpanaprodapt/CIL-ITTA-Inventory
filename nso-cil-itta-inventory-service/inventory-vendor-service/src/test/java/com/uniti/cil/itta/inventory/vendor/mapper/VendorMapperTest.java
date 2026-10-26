package com.uniti.cil.itta.inventory.vendor.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import com.uniti.cil.itta.inventory.vendor.support.VendorFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VendorMapperTest {

    private final VendorMapper mapper = new VendorMapper();

    @Test
    void toEntityCopiesEveryField() {
        VendorCreateRequest request = VendorFixtures.createRequest();
        Vendor entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getLegalName()).isEqualTo(request.getLegalName());
        assertThat(entity.getSupportEmail()).isEqualTo(request.getSupportEmail());
        assertThat(entity.getSupportPhone()).isEqualTo(request.getSupportPhone());
        assertThat(entity.getWebsite()).isEqualTo(request.getWebsite());
        assertThat(entity.getTier()).isEqualTo(request.getTier());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        Vendor entity = VendorFixtures.entity();
        VendorResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getLegalName()).isEqualTo(entity.getLegalName());
        assertThat(response.getSupportEmail()).isEqualTo(entity.getSupportEmail());
        assertThat(response.getSupportPhone()).isEqualTo(entity.getSupportPhone());
        assertThat(response.getWebsite()).isEqualTo(entity.getWebsite());
        assertThat(response.getTier()).isEqualTo(entity.getTier());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        Vendor entity = VendorFixtures.entity();
        VendorSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(VendorFixtures.entity())).hasSameSizeAs(VendorMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Vendor entity = VendorFixtures.entity();
        VendorUpdateRequest request = new VendorUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setLegalName("legalName-3");
        request.setSupportEmail("supportEmail-3");
        request.setSupportPhone("supportPhone-3");
        request.setWebsite("website-3");
        request.setTier(VendorTier.APPROVED);
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(VendorLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getLegalName()).isEqualTo("legalName-3");
        assertThat(entity.getSupportEmail()).isEqualTo("supportEmail-3");
        assertThat(entity.getSupportPhone()).isEqualTo("supportPhone-3");
        assertThat(entity.getWebsite()).isEqualTo("website-3");
        assertThat(entity.getTier()).isEqualTo(VendorTier.APPROVED);
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(VendorLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Vendor entity = VendorFixtures.entity();
        Vendor before = VendorFixtures.entity();
        mapper.applyUpdate(new VendorUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
