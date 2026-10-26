package com.uniti.cil.itta.inventory.device.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.device.dto.*;
import com.uniti.cil.itta.inventory.device.model.*;
import com.uniti.cil.itta.inventory.device.support.NetworkDeviceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class NetworkDeviceMapperTest {

    private final NetworkDeviceMapper mapper = new NetworkDeviceMapper();

    @Test
    void toEntityCopiesEveryField() {
        NetworkDeviceCreateRequest request = NetworkDeviceFixtures.createRequest();
        NetworkDevice entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getHostname()).isEqualTo(request.getHostname());
        assertThat(entity.getDeviceRole()).isEqualTo(request.getDeviceRole());
        assertThat(entity.getMgmtIp()).isEqualTo(request.getMgmtIp());
        assertThat(entity.getSerialNumber()).isEqualTo(request.getSerialNumber());
        assertThat(entity.getModelId()).isEqualTo(request.getModelId());
        assertThat(entity.getRackUnitId()).isEqualTo(request.getRackUnitId());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        NetworkDeviceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getHostname()).isEqualTo(entity.getHostname());
        assertThat(response.getDeviceRole()).isEqualTo(entity.getDeviceRole());
        assertThat(response.getMgmtIp()).isEqualTo(entity.getMgmtIp());
        assertThat(response.getSerialNumber()).isEqualTo(entity.getSerialNumber());
        assertThat(response.getModelId()).isEqualTo(entity.getModelId());
        assertThat(response.getRackUnitId()).isEqualTo(entity.getRackUnitId());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        NetworkDeviceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(NetworkDeviceFixtures.entity())).hasSameSizeAs(NetworkDeviceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        NetworkDeviceUpdateRequest request = new NetworkDeviceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setHostname("hostname-3");
        request.setDeviceRole(NetworkDeviceDeviceRole.AGG);
        request.setMgmtIp("mgmtIp-3");
        request.setSerialNumber("serialNumber-3");
        request.setModelId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setRackUnitId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(NetworkDeviceLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getHostname()).isEqualTo("hostname-3");
        assertThat(entity.getDeviceRole()).isEqualTo(NetworkDeviceDeviceRole.AGG);
        assertThat(entity.getMgmtIp()).isEqualTo("mgmtIp-3");
        assertThat(entity.getSerialNumber()).isEqualTo("serialNumber-3");
        assertThat(entity.getModelId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getRackUnitId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(NetworkDeviceLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        NetworkDevice entity = NetworkDeviceFixtures.entity();
        NetworkDevice before = NetworkDeviceFixtures.entity();
        mapper.applyUpdate(new NetworkDeviceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
