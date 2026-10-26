package com.uniti.cil.itta.inventory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import com.uniti.cil.itta.inventory.service.support.ServiceInstanceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceInstanceMapperTest {

    private final ServiceInstanceMapper mapper = new ServiceInstanceMapper();

    @Test
    void toEntityCopiesEveryField() {
        ServiceInstanceCreateRequest request = ServiceInstanceFixtures.createRequest();
        ServiceInstance entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCatalogItemId()).isEqualTo(request.getCatalogItemId());
        assertThat(entity.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(entity.getActivatedOn()).isEqualTo(request.getActivatedOn());
        assertThat(entity.getBandwidthMbps()).isEqualTo(request.getBandwidthMbps());
        assertThat(entity.getServiceState()).isEqualTo(request.getServiceState());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        ServiceInstanceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCatalogItemId()).isEqualTo(entity.getCatalogItemId());
        assertThat(response.getCustomerId()).isEqualTo(entity.getCustomerId());
        assertThat(response.getActivatedOn()).isEqualTo(entity.getActivatedOn());
        assertThat(response.getBandwidthMbps()).isEqualTo(entity.getBandwidthMbps());
        assertThat(response.getServiceState()).isEqualTo(entity.getServiceState());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        ServiceInstanceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ServiceInstanceFixtures.entity())).hasSameSizeAs(ServiceInstanceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        ServiceInstanceUpdateRequest request = new ServiceInstanceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCatalogItemId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setActivatedOn(LocalDate.of(2026, 4, 13));
        request.setBandwidthMbps(1003L);
        request.setServiceState(ServiceInstanceServiceState.ACTIVE);
        request.setLifecycleStage(ServiceInstanceLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(ServiceInstanceCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCatalogItemId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getCustomerId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getActivatedOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getBandwidthMbps()).isEqualTo(1003L);
        assertThat(entity.getServiceState()).isEqualTo(ServiceInstanceServiceState.ACTIVE);
        assertThat(entity.getLifecycleStage()).isEqualTo(ServiceInstanceLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(ServiceInstanceCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ServiceInstance entity = ServiceInstanceFixtures.entity();
        ServiceInstance before = ServiceInstanceFixtures.entity();
        mapper.applyUpdate(new ServiceInstanceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
