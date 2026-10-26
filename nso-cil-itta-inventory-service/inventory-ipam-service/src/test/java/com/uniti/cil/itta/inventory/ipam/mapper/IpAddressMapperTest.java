package com.uniti.cil.itta.inventory.ipam.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import com.uniti.cil.itta.inventory.ipam.support.IpAddressFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IpAddressMapperTest {

    private final IpAddressMapper mapper = new IpAddressMapper();

    @Test
    void toEntityCopiesEveryField() {
        IpAddressCreateRequest request = IpAddressFixtures.createRequest();
        IpAddress entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSubnetId()).isEqualTo(request.getSubnetId());
        assertThat(entity.getAddress()).isEqualTo(request.getAddress());
        assertThat(entity.getAssignedToId()).isEqualTo(request.getAssignedToId());
        assertThat(entity.getAssignmentType()).isEqualTo(request.getAssignmentType());
        assertThat(entity.getDnsName()).isEqualTo(request.getDnsName());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        IpAddress entity = IpAddressFixtures.entity();
        IpAddressResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSubnetId()).isEqualTo(entity.getSubnetId());
        assertThat(response.getAddress()).isEqualTo(entity.getAddress());
        assertThat(response.getAssignedToId()).isEqualTo(entity.getAssignedToId());
        assertThat(response.getAssignmentType()).isEqualTo(entity.getAssignmentType());
        assertThat(response.getDnsName()).isEqualTo(entity.getDnsName());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        IpAddress entity = IpAddressFixtures.entity();
        IpAddressSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(IpAddressFixtures.entity())).hasSameSizeAs(IpAddressMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        IpAddress entity = IpAddressFixtures.entity();
        IpAddressUpdateRequest request = new IpAddressUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSubnetId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setAddress("address-3");
        request.setAssignedToId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setAssignmentType(IpAddressAssignmentType.RESERVED);
        request.setDnsName("dnsName-3");
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
        assertThat(entity.getSubnetId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getAddress()).isEqualTo("address-3");
        assertThat(entity.getAssignedToId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getAssignmentType()).isEqualTo(IpAddressAssignmentType.RESERVED);
        assertThat(entity.getDnsName()).isEqualTo("dnsName-3");
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        IpAddress entity = IpAddressFixtures.entity();
        IpAddress before = IpAddressFixtures.entity();
        mapper.applyUpdate(new IpAddressUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
