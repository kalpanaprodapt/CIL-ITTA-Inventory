package com.uniti.cil.itta.inventory.mpls.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import com.uniti.cil.itta.inventory.mpls.support.TunnelEndpointFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TunnelEndpointMapperTest {

    private final TunnelEndpointMapper mapper = new TunnelEndpointMapper();

    @Test
    void toEntityCopiesEveryField() {
        TunnelEndpointCreateRequest request = TunnelEndpointFixtures.createRequest();
        TunnelEndpoint entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getDeviceId()).isEqualTo(request.getDeviceId());
        assertThat(entity.getTunnelName()).isEqualTo(request.getTunnelName());
        assertThat(entity.getTunnelType()).isEqualTo(request.getTunnelType());
        assertThat(entity.getSourceIp()).isEqualTo(request.getSourceIp());
        assertThat(entity.getDestinationIp()).isEqualTo(request.getDestinationIp());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        TunnelEndpointResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getDeviceId()).isEqualTo(entity.getDeviceId());
        assertThat(response.getTunnelName()).isEqualTo(entity.getTunnelName());
        assertThat(response.getTunnelType()).isEqualTo(entity.getTunnelType());
        assertThat(response.getSourceIp()).isEqualTo(entity.getSourceIp());
        assertThat(response.getDestinationIp()).isEqualTo(entity.getDestinationIp());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        TunnelEndpointSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(TunnelEndpointFixtures.entity())).hasSameSizeAs(TunnelEndpointMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        TunnelEndpointUpdateRequest request = new TunnelEndpointUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTunnelName("tunnelName-3");
        request.setTunnelType(TunnelEndpointTunnelType.VXLAN);
        request.setSourceIp("sourceIp-3");
        request.setDestinationIp("destinationIp-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(TunnelEndpointLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTunnelName()).isEqualTo("tunnelName-3");
        assertThat(entity.getTunnelType()).isEqualTo(TunnelEndpointTunnelType.VXLAN);
        assertThat(entity.getSourceIp()).isEqualTo("sourceIp-3");
        assertThat(entity.getDestinationIp()).isEqualTo("destinationIp-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(TunnelEndpointLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        TunnelEndpoint entity = TunnelEndpointFixtures.entity();
        TunnelEndpoint before = TunnelEndpointFixtures.entity();
        mapper.applyUpdate(new TunnelEndpointUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
