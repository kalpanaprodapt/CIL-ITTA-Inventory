package com.uniti.cil.itta.inventory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import com.uniti.cil.itta.inventory.service.support.ServiceOrderFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceOrderMapperTest {

    private final ServiceOrderMapper mapper = new ServiceOrderMapper();

    @Test
    void toEntityCopiesEveryField() {
        ServiceOrderCreateRequest request = ServiceOrderFixtures.createRequest();
        ServiceOrder entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(entity.getOrderType()).isEqualTo(request.getOrderType());
        assertThat(entity.getRequestedOn()).isEqualTo(request.getRequestedOn());
        assertThat(entity.getTargetDate()).isEqualTo(request.getTargetDate());
        assertThat(entity.getPriority()).isEqualTo(request.getPriority());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        ServiceOrderResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCustomerId()).isEqualTo(entity.getCustomerId());
        assertThat(response.getOrderType()).isEqualTo(entity.getOrderType());
        assertThat(response.getRequestedOn()).isEqualTo(entity.getRequestedOn());
        assertThat(response.getTargetDate()).isEqualTo(entity.getTargetDate());
        assertThat(response.getPriority()).isEqualTo(entity.getPriority());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        ServiceOrderSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ServiceOrderFixtures.entity())).hasSameSizeAs(ServiceOrderMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        ServiceOrderUpdateRequest request = new ServiceOrderUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setOrderType(ServiceOrderOrderType.DISCONNECT);
        request.setRequestedOn(LocalDate.of(2026, 4, 13));
        request.setTargetDate(LocalDate.of(2026, 4, 13));
        request.setPriority(13);
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(ServiceOrderCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCustomerId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getOrderType()).isEqualTo(ServiceOrderOrderType.DISCONNECT);
        assertThat(entity.getRequestedOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getTargetDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getPriority()).isEqualTo(13);
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(ServiceOrderCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ServiceOrder entity = ServiceOrderFixtures.entity();
        ServiceOrder before = ServiceOrderFixtures.entity();
        mapper.applyUpdate(new ServiceOrderUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
