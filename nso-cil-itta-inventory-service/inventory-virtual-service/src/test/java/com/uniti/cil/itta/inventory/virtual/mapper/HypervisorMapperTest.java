package com.uniti.cil.itta.inventory.virtual.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import com.uniti.cil.itta.inventory.virtual.support.HypervisorFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class HypervisorMapperTest {

    private final HypervisorMapper mapper = new HypervisorMapper();

    @Test
    void toEntityCopiesEveryField() {
        HypervisorCreateRequest request = HypervisorFixtures.createRequest();
        Hypervisor entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getHostDeviceId()).isEqualTo(request.getHostDeviceId());
        assertThat(entity.getHypervisorType()).isEqualTo(request.getHypervisorType());
        assertThat(entity.getHvVersion()).isEqualTo(request.getHvVersion());
        assertThat(entity.getVcpuCapacity()).isEqualTo(request.getVcpuCapacity());
        assertThat(entity.getMemoryGbCapacity()).isEqualTo(request.getMemoryGbCapacity());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        Hypervisor entity = HypervisorFixtures.entity();
        HypervisorResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getHostDeviceId()).isEqualTo(entity.getHostDeviceId());
        assertThat(response.getHypervisorType()).isEqualTo(entity.getHypervisorType());
        assertThat(response.getHvVersion()).isEqualTo(entity.getHvVersion());
        assertThat(response.getVcpuCapacity()).isEqualTo(entity.getVcpuCapacity());
        assertThat(response.getMemoryGbCapacity()).isEqualTo(entity.getMemoryGbCapacity());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        Hypervisor entity = HypervisorFixtures.entity();
        HypervisorSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(HypervisorFixtures.entity())).hasSameSizeAs(HypervisorMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Hypervisor entity = HypervisorFixtures.entity();
        HypervisorUpdateRequest request = new HypervisorUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setHostDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setHypervisorType(HypervisorHypervisorType.HYPERV);
        request.setHvVersion("hvVersion-3");
        request.setVcpuCapacity(13);
        request.setMemoryGbCapacity(13);
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(HypervisorCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getHostDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getHypervisorType()).isEqualTo(HypervisorHypervisorType.HYPERV);
        assertThat(entity.getHvVersion()).isEqualTo("hvVersion-3");
        assertThat(entity.getVcpuCapacity()).isEqualTo(13);
        assertThat(entity.getMemoryGbCapacity()).isEqualTo(13);
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(HypervisorCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Hypervisor entity = HypervisorFixtures.entity();
        Hypervisor before = HypervisorFixtures.entity();
        mapper.applyUpdate(new HypervisorUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
