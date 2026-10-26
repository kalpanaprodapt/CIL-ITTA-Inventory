package com.uniti.cil.itta.inventory.ipam.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import com.uniti.cil.itta.inventory.ipam.support.IpSubnetFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IpSubnetMapperTest {

    private final IpSubnetMapper mapper = new IpSubnetMapper();

    @Test
    void toEntityCopiesEveryField() {
        IpSubnetCreateRequest request = IpSubnetFixtures.createRequest();
        IpSubnet entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCidr()).isEqualTo(request.getCidr());
        assertThat(entity.getIpVersion()).isEqualTo(request.getIpVersion());
        assertThat(entity.getVrfId()).isEqualTo(request.getVrfId());
        assertThat(entity.getGateway()).isEqualTo(request.getGateway());
        assertThat(entity.getVlanId()).isEqualTo(request.getVlanId());
        assertThat(entity.getUtilizationPct()).isEqualTo(request.getUtilizationPct());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        IpSubnet entity = IpSubnetFixtures.entity();
        IpSubnetResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCidr()).isEqualTo(entity.getCidr());
        assertThat(response.getIpVersion()).isEqualTo(entity.getIpVersion());
        assertThat(response.getVrfId()).isEqualTo(entity.getVrfId());
        assertThat(response.getGateway()).isEqualTo(entity.getGateway());
        assertThat(response.getVlanId()).isEqualTo(entity.getVlanId());
        assertThat(response.getUtilizationPct()).isEqualTo(entity.getUtilizationPct());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        IpSubnet entity = IpSubnetFixtures.entity();
        IpSubnetSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(IpSubnetFixtures.entity())).hasSameSizeAs(IpSubnetMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        IpSubnet entity = IpSubnetFixtures.entity();
        IpSubnetUpdateRequest request = new IpSubnetUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCidr("cidr-3");
        request.setIpVersion(IpSubnetIpVersion.V4);
        request.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setGateway("gateway-3");
        request.setVlanId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setUtilizationPct(new BigDecimal("15.50"));
        request.setOwner("owner-3");
        request.setCriticality(IpSubnetCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCidr()).isEqualTo("cidr-3");
        assertThat(entity.getIpVersion()).isEqualTo(IpSubnetIpVersion.V4);
        assertThat(entity.getVrfId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getGateway()).isEqualTo("gateway-3");
        assertThat(entity.getVlanId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getUtilizationPct()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(IpSubnetCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        IpSubnet entity = IpSubnetFixtures.entity();
        IpSubnet before = IpSubnetFixtures.entity();
        mapper.applyUpdate(new IpSubnetUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
