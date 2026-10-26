package com.uniti.cil.itta.inventory.assetmgmt.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.assetmgmt.dto.*;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import com.uniti.cil.itta.inventory.assetmgmt.support.PurchaseOrderFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PurchaseOrderMapperTest {

    private final PurchaseOrderMapper mapper = new PurchaseOrderMapper();

    @Test
    void toEntityCopiesEveryField() {
        PurchaseOrderCreateRequest request = PurchaseOrderFixtures.createRequest();
        PurchaseOrder entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getPoNumber()).isEqualTo(request.getPoNumber());
        assertThat(entity.getVendorId()).isEqualTo(request.getVendorId());
        assertThat(entity.getOrderedOn()).isEqualTo(request.getOrderedOn());
        assertThat(entity.getTotalAmount()).isEqualTo(request.getTotalAmount());
        assertThat(entity.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        PurchaseOrderResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getPoNumber()).isEqualTo(entity.getPoNumber());
        assertThat(response.getVendorId()).isEqualTo(entity.getVendorId());
        assertThat(response.getOrderedOn()).isEqualTo(entity.getOrderedOn());
        assertThat(response.getTotalAmount()).isEqualTo(entity.getTotalAmount());
        assertThat(response.getCurrency()).isEqualTo(entity.getCurrency());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        PurchaseOrderSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PurchaseOrderFixtures.entity())).hasSameSizeAs(PurchaseOrderMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        PurchaseOrderUpdateRequest request = new PurchaseOrderUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setPoNumber("poNumber-3");
        request.setVendorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setOrderedOn(LocalDate.of(2026, 4, 13));
        request.setTotalAmount(new BigDecimal("15.50"));
        request.setCurrency("currency-3");
        request.setOwner("owner-3");
        request.setCriticality(PurchaseOrderCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getPoNumber()).isEqualTo("poNumber-3");
        assertThat(entity.getVendorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getOrderedOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getTotalAmount()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCurrency()).isEqualTo("currency-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(PurchaseOrderCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        PurchaseOrder entity = PurchaseOrderFixtures.entity();
        PurchaseOrder before = PurchaseOrderFixtures.entity();
        mapper.applyUpdate(new PurchaseOrderUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
