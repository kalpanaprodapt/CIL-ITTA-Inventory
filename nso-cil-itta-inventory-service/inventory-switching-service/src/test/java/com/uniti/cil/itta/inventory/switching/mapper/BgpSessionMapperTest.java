package com.uniti.cil.itta.inventory.switching.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import com.uniti.cil.itta.inventory.switching.support.BgpSessionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BgpSessionMapperTest {

    private final BgpSessionMapper mapper = new BgpSessionMapper();

    @Test
    void toEntityCopiesEveryField() {
        BgpSessionCreateRequest request = BgpSessionFixtures.createRequest();
        BgpSession entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getLocalDeviceId()).isEqualTo(request.getLocalDeviceId());
        assertThat(entity.getPeerAddress()).isEqualTo(request.getPeerAddress());
        assertThat(entity.getLocalAsn()).isEqualTo(request.getLocalAsn());
        assertThat(entity.getRemoteAsn()).isEqualTo(request.getRemoteAsn());
        assertThat(entity.getSessionState()).isEqualTo(request.getSessionState());
        assertThat(entity.getPrefixLimit()).isEqualTo(request.getPrefixLimit());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        BgpSession entity = BgpSessionFixtures.entity();
        BgpSessionResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getLocalDeviceId()).isEqualTo(entity.getLocalDeviceId());
        assertThat(response.getPeerAddress()).isEqualTo(entity.getPeerAddress());
        assertThat(response.getLocalAsn()).isEqualTo(entity.getLocalAsn());
        assertThat(response.getRemoteAsn()).isEqualTo(entity.getRemoteAsn());
        assertThat(response.getSessionState()).isEqualTo(entity.getSessionState());
        assertThat(response.getPrefixLimit()).isEqualTo(entity.getPrefixLimit());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        BgpSession entity = BgpSessionFixtures.entity();
        BgpSessionSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(BgpSessionFixtures.entity())).hasSameSizeAs(BgpSessionMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        BgpSession entity = BgpSessionFixtures.entity();
        BgpSessionUpdateRequest request = new BgpSessionUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setLocalDeviceId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setPeerAddress("peerAddress-3");
        request.setLocalAsn(1003L);
        request.setRemoteAsn(1003L);
        request.setSessionState(BgpSessionSessionState.ACTIVE);
        request.setPrefixLimit(13);
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(BgpSessionCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getLocalDeviceId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getPeerAddress()).isEqualTo("peerAddress-3");
        assertThat(entity.getLocalAsn()).isEqualTo(1003L);
        assertThat(entity.getRemoteAsn()).isEqualTo(1003L);
        assertThat(entity.getSessionState()).isEqualTo(BgpSessionSessionState.ACTIVE);
        assertThat(entity.getPrefixLimit()).isEqualTo(13);
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(BgpSessionCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        BgpSession entity = BgpSessionFixtures.entity();
        BgpSession before = BgpSessionFixtures.entity();
        mapper.applyUpdate(new BgpSessionUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
