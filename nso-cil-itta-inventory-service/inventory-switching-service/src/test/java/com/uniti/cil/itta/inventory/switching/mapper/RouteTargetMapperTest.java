package com.uniti.cil.itta.inventory.switching.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.switching.dto.*;
import com.uniti.cil.itta.inventory.switching.model.*;
import com.uniti.cil.itta.inventory.switching.support.RouteTargetFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RouteTargetMapperTest {

    private final RouteTargetMapper mapper = new RouteTargetMapper();

    @Test
    void toEntityCopiesEveryField() {
        RouteTargetCreateRequest request = RouteTargetFixtures.createRequest();
        RouteTarget entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getAsn()).isEqualTo(request.getAsn());
        assertThat(entity.getAssignedNumber()).isEqualTo(request.getAssignedNumber());
        assertThat(entity.getRtType()).isEqualTo(request.getRtType());
        assertThat(entity.getVrfId()).isEqualTo(request.getVrfId());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        RouteTarget entity = RouteTargetFixtures.entity();
        RouteTargetResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getAsn()).isEqualTo(entity.getAsn());
        assertThat(response.getAssignedNumber()).isEqualTo(entity.getAssignedNumber());
        assertThat(response.getRtType()).isEqualTo(entity.getRtType());
        assertThat(response.getVrfId()).isEqualTo(entity.getVrfId());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        RouteTarget entity = RouteTargetFixtures.entity();
        RouteTargetSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(RouteTargetFixtures.entity())).hasSameSizeAs(RouteTargetMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        RouteTarget entity = RouteTargetFixtures.entity();
        RouteTargetUpdateRequest request = new RouteTargetUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setAsn(1003L);
        request.setAssignedNumber(1003L);
        request.setRtType(RouteTargetRtType.BOTH);
        request.setVrfId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLifecycleStage(RouteTargetLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(RouteTargetCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getAsn()).isEqualTo(1003L);
        assertThat(entity.getAssignedNumber()).isEqualTo(1003L);
        assertThat(entity.getRtType()).isEqualTo(RouteTargetRtType.BOTH);
        assertThat(entity.getVrfId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLifecycleStage()).isEqualTo(RouteTargetLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(RouteTargetCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        RouteTarget entity = RouteTargetFixtures.entity();
        RouteTarget before = RouteTargetFixtures.entity();
        mapper.applyUpdate(new RouteTargetUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
