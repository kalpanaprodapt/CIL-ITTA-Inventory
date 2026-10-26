package com.uniti.cil.itta.inventory.integration.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import com.uniti.cil.itta.inventory.integration.support.IntegrationEndpointFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IntegrationEndpointMapperTest {

    private final IntegrationEndpointMapper mapper = new IntegrationEndpointMapper();

    @Test
    void toEntityCopiesEveryField() {
        IntegrationEndpointCreateRequest request = IntegrationEndpointFixtures.createRequest();
        IntegrationEndpoint entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getEndpointName()).isEqualTo(request.getEndpointName());
        assertThat(entity.getTargetSystem()).isEqualTo(request.getTargetSystem());
        assertThat(entity.getBaseUrl()).isEqualTo(request.getBaseUrl());
        assertThat(entity.getAuthType()).isEqualTo(request.getAuthType());
        assertThat(entity.getTimeoutMs()).isEqualTo(request.getTimeoutMs());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        IntegrationEndpointResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getEndpointName()).isEqualTo(entity.getEndpointName());
        assertThat(response.getTargetSystem()).isEqualTo(entity.getTargetSystem());
        assertThat(response.getBaseUrl()).isEqualTo(entity.getBaseUrl());
        assertThat(response.getAuthType()).isEqualTo(entity.getAuthType());
        assertThat(response.getTimeoutMs()).isEqualTo(entity.getTimeoutMs());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        IntegrationEndpointSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(IntegrationEndpointFixtures.entity())).hasSameSizeAs(IntegrationEndpointMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        IntegrationEndpointUpdateRequest request = new IntegrationEndpointUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setEndpointName("endpointName-3");
        request.setTargetSystem("targetSystem-3");
        request.setBaseUrl("baseUrl-3");
        request.setAuthType(IntegrationEndpointAuthType.MTLS);
        request.setTimeoutMs(13);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(IntegrationEndpointCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getEndpointName()).isEqualTo("endpointName-3");
        assertThat(entity.getTargetSystem()).isEqualTo("targetSystem-3");
        assertThat(entity.getBaseUrl()).isEqualTo("baseUrl-3");
        assertThat(entity.getAuthType()).isEqualTo(IntegrationEndpointAuthType.MTLS);
        assertThat(entity.getTimeoutMs()).isEqualTo(13);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(IntegrationEndpointCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        IntegrationEndpoint entity = IntegrationEndpointFixtures.entity();
        IntegrationEndpoint before = IntegrationEndpointFixtures.entity();
        mapper.applyUpdate(new IntegrationEndpointUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
