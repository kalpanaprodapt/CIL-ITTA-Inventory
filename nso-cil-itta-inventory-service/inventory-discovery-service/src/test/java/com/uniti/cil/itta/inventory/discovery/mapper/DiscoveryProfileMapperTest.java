package com.uniti.cil.itta.inventory.discovery.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.discovery.dto.*;
import com.uniti.cil.itta.inventory.discovery.model.*;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveryProfileFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscoveryProfileMapperTest {

    private final DiscoveryProfileMapper mapper = new DiscoveryProfileMapper();

    @Test
    void toEntityCopiesEveryField() {
        DiscoveryProfileCreateRequest request = DiscoveryProfileFixtures.createRequest();
        DiscoveryProfile entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getProfileName()).isEqualTo(request.getProfileName());
        assertThat(entity.getProtocol()).isEqualTo(request.getProtocol());
        assertThat(entity.getCredentialRef()).isEqualTo(request.getCredentialRef());
        assertThat(entity.getScopeCidr()).isEqualTo(request.getScopeCidr());
        assertThat(entity.getScheduleCron()).isEqualTo(request.getScheduleCron());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
    }

    @Test
    void toResponseCopiesEveryField() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        DiscoveryProfileResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getProfileName()).isEqualTo(entity.getProfileName());
        assertThat(response.getProtocol()).isEqualTo(entity.getProtocol());
        assertThat(response.getCredentialRef()).isEqualTo(entity.getCredentialRef());
        assertThat(response.getScopeCidr()).isEqualTo(entity.getScopeCidr());
        assertThat(response.getScheduleCron()).isEqualTo(entity.getScheduleCron());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
    }

    @Test
    void summaryCarriesIdentity() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        DiscoveryProfileSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(DiscoveryProfileFixtures.entity())).hasSameSizeAs(DiscoveryProfileMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        DiscoveryProfileUpdateRequest request = new DiscoveryProfileUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setProfileName("profileName-3");
        request.setProtocol(DiscoveryProfileProtocol.NETCONF);
        request.setCredentialRef("credentialRef-3");
        request.setScopeCidr("scopeCidr-3");
        request.setScheduleCron("scheduleCron-3");
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
        assertThat(entity.getProfileName()).isEqualTo("profileName-3");
        assertThat(entity.getProtocol()).isEqualTo(DiscoveryProfileProtocol.NETCONF);
        assertThat(entity.getCredentialRef()).isEqualTo("credentialRef-3");
        assertThat(entity.getScopeCidr()).isEqualTo("scopeCidr-3");
        assertThat(entity.getScheduleCron()).isEqualTo("scheduleCron-3");
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        DiscoveryProfile before = DiscoveryProfileFixtures.entity();
        mapper.applyUpdate(new DiscoveryProfileUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
