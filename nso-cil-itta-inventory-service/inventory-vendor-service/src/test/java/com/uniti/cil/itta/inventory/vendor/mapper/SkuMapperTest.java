package com.uniti.cil.itta.inventory.vendor.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.vendor.dto.*;
import com.uniti.cil.itta.inventory.vendor.model.*;
import com.uniti.cil.itta.inventory.vendor.support.SkuFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SkuMapperTest {

    private final SkuMapper mapper = new SkuMapper();

    @Test
    void toEntityCopiesEveryField() {
        SkuCreateRequest request = SkuFixtures.createRequest();
        Sku entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getModelId()).isEqualTo(request.getModelId());
        assertThat(entity.getSkuCode()).isEqualTo(request.getSkuCode());
        assertThat(entity.getListPrice()).isEqualTo(request.getListPrice());
        assertThat(entity.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(entity.getLeadTimeDays()).isEqualTo(request.getLeadTimeDays());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        Sku entity = SkuFixtures.entity();
        SkuResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getModelId()).isEqualTo(entity.getModelId());
        assertThat(response.getSkuCode()).isEqualTo(entity.getSkuCode());
        assertThat(response.getListPrice()).isEqualTo(entity.getListPrice());
        assertThat(response.getCurrency()).isEqualTo(entity.getCurrency());
        assertThat(response.getLeadTimeDays()).isEqualTo(entity.getLeadTimeDays());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        Sku entity = SkuFixtures.entity();
        SkuSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SkuFixtures.entity())).hasSameSizeAs(SkuMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Sku entity = SkuFixtures.entity();
        SkuUpdateRequest request = new SkuUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setModelId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSkuCode("skuCode-3");
        request.setListPrice(new BigDecimal("15.50"));
        request.setCurrency("currency-3");
        request.setLeadTimeDays(13);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(SkuCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getModelId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSkuCode()).isEqualTo("skuCode-3");
        assertThat(entity.getListPrice()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCurrency()).isEqualTo("currency-3");
        assertThat(entity.getLeadTimeDays()).isEqualTo(13);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SkuCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Sku entity = SkuFixtures.entity();
        Sku before = SkuFixtures.entity();
        mapper.applyUpdate(new SkuUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
