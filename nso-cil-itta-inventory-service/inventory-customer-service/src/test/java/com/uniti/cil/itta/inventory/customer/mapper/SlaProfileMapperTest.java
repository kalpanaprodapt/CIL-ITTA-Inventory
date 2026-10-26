package com.uniti.cil.itta.inventory.customer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import com.uniti.cil.itta.inventory.customer.support.SlaProfileFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SlaProfileMapperTest {

    private final SlaProfileMapper mapper = new SlaProfileMapper();

    @Test
    void toEntityCopiesEveryField() {
        SlaProfileCreateRequest request = SlaProfileFixtures.createRequest();
        SlaProfile entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getProfileName()).isEqualTo(request.getProfileName());
        assertThat(entity.getAvailabilityPct()).isEqualTo(request.getAvailabilityPct());
        assertThat(entity.getMttrMinutes()).isEqualTo(request.getMttrMinutes());
        assertThat(entity.getLatencyMs()).isEqualTo(request.getLatencyMs());
        assertThat(entity.getJitterMs()).isEqualTo(request.getJitterMs());
        assertThat(entity.getCreditPolicy()).isEqualTo(request.getCreditPolicy());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        SlaProfile entity = SlaProfileFixtures.entity();
        SlaProfileResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getProfileName()).isEqualTo(entity.getProfileName());
        assertThat(response.getAvailabilityPct()).isEqualTo(entity.getAvailabilityPct());
        assertThat(response.getMttrMinutes()).isEqualTo(entity.getMttrMinutes());
        assertThat(response.getLatencyMs()).isEqualTo(entity.getLatencyMs());
        assertThat(response.getJitterMs()).isEqualTo(entity.getJitterMs());
        assertThat(response.getCreditPolicy()).isEqualTo(entity.getCreditPolicy());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        SlaProfile entity = SlaProfileFixtures.entity();
        SlaProfileSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SlaProfileFixtures.entity())).hasSameSizeAs(SlaProfileMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        SlaProfile entity = SlaProfileFixtures.entity();
        SlaProfileUpdateRequest request = new SlaProfileUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setProfileName("profileName-3");
        request.setAvailabilityPct(new BigDecimal("15.50"));
        request.setMttrMinutes(13);
        request.setLatencyMs(new BigDecimal("15.50"));
        request.setJitterMs(new BigDecimal("15.50"));
        request.setCreditPolicy("creditPolicy-3");
        request.setOwner("owner-3");
        request.setCriticality(SlaProfileCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getProfileName()).isEqualTo("profileName-3");
        assertThat(entity.getAvailabilityPct()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getMttrMinutes()).isEqualTo(13);
        assertThat(entity.getLatencyMs()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getJitterMs()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getCreditPolicy()).isEqualTo("creditPolicy-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SlaProfileCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        SlaProfile entity = SlaProfileFixtures.entity();
        SlaProfile before = SlaProfileFixtures.entity();
        mapper.applyUpdate(new SlaProfileUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
