package com.uniti.cil.itta.inventory.port.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import com.uniti.cil.itta.inventory.port.support.InterfaceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class InterfaceMapperTest {

    private final InterfaceMapper mapper = new InterfaceMapper();

    @Test
    void toEntityCopiesEveryField() {
        InterfaceCreateRequest request = InterfaceFixtures.createRequest();
        Interface entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getPortId()).isEqualTo(request.getPortId());
        assertThat(entity.getIfName()).isEqualTo(request.getIfName());
        assertThat(entity.getIfIndex()).isEqualTo(request.getIfIndex());
        assertThat(entity.getMacAddress()).isEqualTo(request.getMacAddress());
        assertThat(entity.getMtu()).isEqualTo(request.getMtu());
        assertThat(entity.getOperState()).isEqualTo(request.getOperState());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        Interface entity = InterfaceFixtures.entity();
        InterfaceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getPortId()).isEqualTo(entity.getPortId());
        assertThat(response.getIfName()).isEqualTo(entity.getIfName());
        assertThat(response.getIfIndex()).isEqualTo(entity.getIfIndex());
        assertThat(response.getMacAddress()).isEqualTo(entity.getMacAddress());
        assertThat(response.getMtu()).isEqualTo(entity.getMtu());
        assertThat(response.getOperState()).isEqualTo(entity.getOperState());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        Interface entity = InterfaceFixtures.entity();
        InterfaceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(InterfaceFixtures.entity())).hasSameSizeAs(InterfaceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Interface entity = InterfaceFixtures.entity();
        InterfaceUpdateRequest request = new InterfaceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setPortId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setIfName("ifName-3");
        request.setIfIndex(13);
        request.setMacAddress("macAddress-3");
        request.setMtu(13);
        request.setOperState(InterfaceOperState.TESTING);
        request.setOwner("owner-3");
        request.setCriticality(InterfaceCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getPortId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getIfName()).isEqualTo("ifName-3");
        assertThat(entity.getIfIndex()).isEqualTo(13);
        assertThat(entity.getMacAddress()).isEqualTo("macAddress-3");
        assertThat(entity.getMtu()).isEqualTo(13);
        assertThat(entity.getOperState()).isEqualTo(InterfaceOperState.TESTING);
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(InterfaceCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Interface entity = InterfaceFixtures.entity();
        Interface before = InterfaceFixtures.entity();
        mapper.applyUpdate(new InterfaceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
