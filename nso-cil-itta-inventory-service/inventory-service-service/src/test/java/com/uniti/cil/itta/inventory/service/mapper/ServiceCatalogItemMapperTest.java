package com.uniti.cil.itta.inventory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import com.uniti.cil.itta.inventory.service.support.ServiceCatalogItemFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceCatalogItemMapperTest {

    private final ServiceCatalogItemMapper mapper = new ServiceCatalogItemMapper();

    @Test
    void toEntityCopiesEveryField() {
        ServiceCatalogItemCreateRequest request = ServiceCatalogItemFixtures.createRequest();
        ServiceCatalogItem entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getItemName()).isEqualTo(request.getItemName());
        assertThat(entity.getFamily()).isEqualTo(request.getFamily());
        assertThat(entity.getBasePrice()).isEqualTo(request.getBasePrice());
        assertThat(entity.getBillingUnit()).isEqualTo(request.getBillingUnit());
        assertThat(entity.getOrderable()).isEqualTo(request.getOrderable());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        ServiceCatalogItemResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getItemName()).isEqualTo(entity.getItemName());
        assertThat(response.getFamily()).isEqualTo(entity.getFamily());
        assertThat(response.getBasePrice()).isEqualTo(entity.getBasePrice());
        assertThat(response.getBillingUnit()).isEqualTo(entity.getBillingUnit());
        assertThat(response.getOrderable()).isEqualTo(entity.getOrderable());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        ServiceCatalogItemSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ServiceCatalogItemFixtures.entity())).hasSameSizeAs(ServiceCatalogItemMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        ServiceCatalogItemUpdateRequest request = new ServiceCatalogItemUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setItemName("itemName-3");
        request.setFamily(ServiceCatalogItemFamily.VOICE);
        request.setBasePrice(new BigDecimal("15.50"));
        request.setBillingUnit("billingUnit-3");
        request.setOrderable(Boolean.TRUE);
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(ServiceCatalogItemLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getItemName()).isEqualTo("itemName-3");
        assertThat(entity.getFamily()).isEqualTo(ServiceCatalogItemFamily.VOICE);
        assertThat(entity.getBasePrice()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getBillingUnit()).isEqualTo("billingUnit-3");
        assertThat(entity.getOrderable()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(ServiceCatalogItemLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        ServiceCatalogItem before = ServiceCatalogItemFixtures.entity();
        mapper.applyUpdate(new ServiceCatalogItemUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
