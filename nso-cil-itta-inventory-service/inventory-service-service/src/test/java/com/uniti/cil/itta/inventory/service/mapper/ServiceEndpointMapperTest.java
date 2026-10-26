package com.uniti.cil.itta.inventory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.service.dto.*;
import com.uniti.cil.itta.inventory.service.model.*;
import com.uniti.cil.itta.inventory.service.support.ServiceEndpointFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceEndpointMapperTest {

    private final ServiceEndpointMapper mapper = new ServiceEndpointMapper();

    @Test
    void toEntityCopiesEveryField() {
        ServiceEndpointCreateRequest request = ServiceEndpointFixtures.createRequest();
        ServiceEndpoint entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getServiceId()).isEqualTo(request.getServiceId());
        assertThat(entity.getSiteId()).isEqualTo(request.getSiteId());
        assertThat(entity.getPortId()).isEqualTo(request.getPortId());
        assertThat(entity.getEndpointRole()).isEqualTo(request.getEndpointRole());
        assertThat(entity.getHandoff()).isEqualTo(request.getHandoff());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        ServiceEndpointResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getServiceId()).isEqualTo(entity.getServiceId());
        assertThat(response.getSiteId()).isEqualTo(entity.getSiteId());
        assertThat(response.getPortId()).isEqualTo(entity.getPortId());
        assertThat(response.getEndpointRole()).isEqualTo(entity.getEndpointRole());
        assertThat(response.getHandoff()).isEqualTo(entity.getHandoff());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        ServiceEndpointSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(ServiceEndpointFixtures.entity())).hasSameSizeAs(ServiceEndpointMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        ServiceEndpointUpdateRequest request = new ServiceEndpointUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setServiceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setSiteId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setEndpointRole(ServiceEndpointEndpointRole.PEER);
        request.setHandoff("handoff-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(ServiceEndpointLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getServiceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getSiteId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getPortId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getEndpointRole()).isEqualTo(ServiceEndpointEndpointRole.PEER);
        assertThat(entity.getHandoff()).isEqualTo("handoff-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(ServiceEndpointLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        ServiceEndpoint entity = ServiceEndpointFixtures.entity();
        ServiceEndpoint before = ServiceEndpointFixtures.entity();
        mapper.applyUpdate(new ServiceEndpointUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
