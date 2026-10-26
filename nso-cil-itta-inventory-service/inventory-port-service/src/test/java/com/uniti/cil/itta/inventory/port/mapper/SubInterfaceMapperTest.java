package com.uniti.cil.itta.inventory.port.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.port.dto.*;
import com.uniti.cil.itta.inventory.port.model.*;
import com.uniti.cil.itta.inventory.port.support.SubInterfaceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SubInterfaceMapperTest {

    private final SubInterfaceMapper mapper = new SubInterfaceMapper();

    @Test
    void toEntityCopiesEveryField() {
        SubInterfaceCreateRequest request = SubInterfaceFixtures.createRequest();
        SubInterface entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getInterfaceId()).isEqualTo(request.getInterfaceId());
        assertThat(entity.getVlanTag()).isEqualTo(request.getVlanTag());
        assertThat(entity.getEncapsulation()).isEqualTo(request.getEncapsulation());
        assertThat(entity.getIpv4Address()).isEqualTo(request.getIpv4Address());
        assertThat(entity.getIpv6Address()).isEqualTo(request.getIpv6Address());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        SubInterface entity = SubInterfaceFixtures.entity();
        SubInterfaceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getInterfaceId()).isEqualTo(entity.getInterfaceId());
        assertThat(response.getVlanTag()).isEqualTo(entity.getVlanTag());
        assertThat(response.getEncapsulation()).isEqualTo(entity.getEncapsulation());
        assertThat(response.getIpv4Address()).isEqualTo(entity.getIpv4Address());
        assertThat(response.getIpv6Address()).isEqualTo(entity.getIpv6Address());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        SubInterface entity = SubInterfaceFixtures.entity();
        SubInterfaceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SubInterfaceFixtures.entity())).hasSameSizeAs(SubInterfaceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        SubInterface entity = SubInterfaceFixtures.entity();
        SubInterfaceUpdateRequest request = new SubInterfaceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setVlanTag(13);
        request.setEncapsulation("encapsulation-3");
        request.setIpv4Address("ipv4Address-3");
        request.setIpv6Address("ipv6Address-3");
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getInterfaceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getVlanTag()).isEqualTo(13);
        assertThat(entity.getEncapsulation()).isEqualTo("encapsulation-3");
        assertThat(entity.getIpv4Address()).isEqualTo("ipv4Address-3");
        assertThat(entity.getIpv6Address()).isEqualTo("ipv6Address-3");
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        SubInterface entity = SubInterfaceFixtures.entity();
        SubInterface before = SubInterfaceFixtures.entity();
        mapper.applyUpdate(new SubInterfaceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
