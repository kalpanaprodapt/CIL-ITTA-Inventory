package com.uniti.cil.itta.inventory.virtual.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import com.uniti.cil.itta.inventory.virtual.support.VirtualMachineFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VirtualMachineMapperTest {

    private final VirtualMachineMapper mapper = new VirtualMachineMapper();

    @Test
    void toEntityCopiesEveryField() {
        VirtualMachineCreateRequest request = VirtualMachineFixtures.createRequest();
        VirtualMachine entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getHypervisorId()).isEqualTo(request.getHypervisorId());
        assertThat(entity.getVmName()).isEqualTo(request.getVmName());
        assertThat(entity.getVcpu()).isEqualTo(request.getVcpu());
        assertThat(entity.getMemoryGb()).isEqualTo(request.getMemoryGb());
        assertThat(entity.getDiskGb()).isEqualTo(request.getDiskGb());
        assertThat(entity.getPowerState()).isEqualTo(request.getPowerState());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
    }

    @Test
    void toResponseCopiesEveryField() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        VirtualMachineResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getHypervisorId()).isEqualTo(entity.getHypervisorId());
        assertThat(response.getVmName()).isEqualTo(entity.getVmName());
        assertThat(response.getVcpu()).isEqualTo(entity.getVcpu());
        assertThat(response.getMemoryGb()).isEqualTo(entity.getMemoryGb());
        assertThat(response.getDiskGb()).isEqualTo(entity.getDiskGb());
        assertThat(response.getPowerState()).isEqualTo(entity.getPowerState());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
    }

    @Test
    void summaryCarriesIdentity() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        VirtualMachineSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(VirtualMachineFixtures.entity())).hasSameSizeAs(VirtualMachineMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        VirtualMachineUpdateRequest request = new VirtualMachineUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setHypervisorId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setVmName("vmName-3");
        request.setVcpu(13);
        request.setMemoryGb(13);
        request.setDiskGb(13);
        request.setPowerState(VirtualMachinePowerState.SUSPENDED);
        request.setCriticality(VirtualMachineCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getHypervisorId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getVmName()).isEqualTo("vmName-3");
        assertThat(entity.getVcpu()).isEqualTo(13);
        assertThat(entity.getMemoryGb()).isEqualTo(13);
        assertThat(entity.getDiskGb()).isEqualTo(13);
        assertThat(entity.getPowerState()).isEqualTo(VirtualMachinePowerState.SUSPENDED);
        assertThat(entity.getCriticality()).isEqualTo(VirtualMachineCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        VirtualMachine entity = VirtualMachineFixtures.entity();
        VirtualMachine before = VirtualMachineFixtures.entity();
        mapper.applyUpdate(new VirtualMachineUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
