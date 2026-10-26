package com.uniti.cil.itta.inventory.topology.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.topology.dto.*;
import com.uniti.cil.itta.inventory.topology.model.*;
import com.uniti.cil.itta.inventory.topology.support.LinkFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LinkMapperTest {

    private final LinkMapper mapper = new LinkMapper();

    @Test
    void toEntityCopiesEveryField() {
        LinkCreateRequest request = LinkFixtures.createRequest();
        Link entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAInterfaceId()).isEqualTo(request.getAInterfaceId());
        assertThat(entity.getZInterfaceId()).isEqualTo(request.getZInterfaceId());
        assertThat(entity.getLinkType()).isEqualTo(request.getLinkType());
        assertThat(entity.getSpeedMbps()).isEqualTo(request.getSpeedMbps());
        assertThat(entity.getDiscoveredBy()).isEqualTo(request.getDiscoveredBy());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        Link entity = LinkFixtures.entity();
        LinkResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAInterfaceId()).isEqualTo(entity.getAInterfaceId());
        assertThat(response.getZInterfaceId()).isEqualTo(entity.getZInterfaceId());
        assertThat(response.getLinkType()).isEqualTo(entity.getLinkType());
        assertThat(response.getSpeedMbps()).isEqualTo(entity.getSpeedMbps());
        assertThat(response.getDiscoveredBy()).isEqualTo(entity.getDiscoveredBy());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        Link entity = LinkFixtures.entity();
        LinkSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(LinkFixtures.entity())).hasSameSizeAs(LinkMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Link entity = LinkFixtures.entity();
        LinkUpdateRequest request = new LinkUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setZInterfaceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLinkType(LinkLinkType.VIRTUAL);
        request.setSpeedMbps(1003L);
        request.setDiscoveredBy("discoveredBy-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(LinkCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getAInterfaceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getZInterfaceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLinkType()).isEqualTo(LinkLinkType.VIRTUAL);
        assertThat(entity.getSpeedMbps()).isEqualTo(1003L);
        assertThat(entity.getDiscoveredBy()).isEqualTo("discoveredBy-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(LinkCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Link entity = LinkFixtures.entity();
        Link before = LinkFixtures.entity();
        mapper.applyUpdate(new LinkUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
