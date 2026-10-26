package com.uniti.cil.itta.inventory.alarm.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.alarm.dto.*;
import com.uniti.cil.itta.inventory.alarm.model.*;
import com.uniti.cil.itta.inventory.alarm.support.TicketLinkFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class TicketLinkMapperTest {

    private final TicketLinkMapper mapper = new TicketLinkMapper();

    @Test
    void toEntityCopiesEveryField() {
        TicketLinkCreateRequest request = TicketLinkFixtures.createRequest();
        TicketLink entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAlarmId()).isEqualTo(request.getAlarmId());
        assertThat(entity.getTicketSystem()).isEqualTo(request.getTicketSystem());
        assertThat(entity.getTicketNumber()).isEqualTo(request.getTicketNumber());
        assertThat(entity.getLinkedAt()).isEqualTo(request.getLinkedAt());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
    }

    @Test
    void toResponseCopiesEveryField() {
        TicketLink entity = TicketLinkFixtures.entity();
        TicketLinkResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAlarmId()).isEqualTo(entity.getAlarmId());
        assertThat(response.getTicketSystem()).isEqualTo(entity.getTicketSystem());
        assertThat(response.getTicketNumber()).isEqualTo(entity.getTicketNumber());
        assertThat(response.getLinkedAt()).isEqualTo(entity.getLinkedAt());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
    }

    @Test
    void summaryCarriesIdentity() {
        TicketLink entity = TicketLinkFixtures.entity();
        TicketLinkSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(TicketLinkFixtures.entity())).hasSameSizeAs(TicketLinkMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        TicketLink entity = TicketLinkFixtures.entity();
        TicketLinkUpdateRequest request = new TicketLinkUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAlarmId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTicketSystem(TicketLinkTicketSystem.REMEDY);
        request.setTicketNumber("ticketNumber-3");
        request.setLinkedAt(Instant.parse("2026-04-13T10:15:30Z"));
        request.setOwner("owner-3");
        request.setCriticality(TicketLinkCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getAlarmId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTicketSystem()).isEqualTo(TicketLinkTicketSystem.REMEDY);
        assertThat(entity.getTicketNumber()).isEqualTo("ticketNumber-3");
        assertThat(entity.getLinkedAt()).isEqualTo(Instant.parse("2026-04-13T10:15:30Z"));
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(TicketLinkCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        TicketLink entity = TicketLinkFixtures.entity();
        TicketLink before = TicketLinkFixtures.entity();
        mapper.applyUpdate(new TicketLinkUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
