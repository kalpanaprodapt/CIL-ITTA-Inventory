package com.uniti.cil.itta.inventory.mpls.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import com.uniti.cil.itta.inventory.mpls.support.VpnServiceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VpnServiceMapperTest {

    private final VpnServiceMapper mapper = new VpnServiceMapper();

    @Test
    void toEntityCopiesEveryField() {
        VpnServiceCreateRequest request = VpnServiceFixtures.createRequest();
        VpnService entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVpnName()).isEqualTo(request.getVpnName());
        assertThat(entity.getVpnType()).isEqualTo(request.getVpnType());
        assertThat(entity.getVrfId()).isEqualTo(request.getVrfId());
        assertThat(entity.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(entity.getTopology()).isEqualTo(request.getTopology());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        VpnService entity = VpnServiceFixtures.entity();
        VpnServiceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVpnName()).isEqualTo(entity.getVpnName());
        assertThat(response.getVpnType()).isEqualTo(entity.getVpnType());
        assertThat(response.getVrfId()).isEqualTo(entity.getVrfId());
        assertThat(response.getCustomerId()).isEqualTo(entity.getCustomerId());
        assertThat(response.getTopology()).isEqualTo(entity.getTopology());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        VpnService entity = VpnServiceFixtures.entity();
        VpnServiceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(VpnServiceFixtures.entity())).hasSameSizeAs(VpnServiceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        VpnService entity = VpnServiceFixtures.entity();
        VpnServiceUpdateRequest request = new VpnServiceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVpnName("vpnName-3");
        request.setVpnType(VpnServiceVpnType.VPLS);
        request.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTopology(VpnServiceTopology.POINT_TO_POINT);
        request.setOwner("owner-3");
        request.setCriticality(VpnServiceCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVpnName()).isEqualTo("vpnName-3");
        assertThat(entity.getVpnType()).isEqualTo(VpnServiceVpnType.VPLS);
        assertThat(entity.getVrfId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getCustomerId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTopology()).isEqualTo(VpnServiceTopology.POINT_TO_POINT);
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(VpnServiceCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        VpnService entity = VpnServiceFixtures.entity();
        VpnService before = VpnServiceFixtures.entity();
        mapper.applyUpdate(new VpnServiceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
