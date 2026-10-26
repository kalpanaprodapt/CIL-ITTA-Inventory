package com.uniti.cil.itta.inventory.optical.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.optical.dto.*;
import com.uniti.cil.itta.inventory.optical.model.*;
import com.uniti.cil.itta.inventory.optical.support.OtnLinkFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class OtnLinkMapperTest {

    private final OtnLinkMapper mapper = new OtnLinkMapper();

    @Test
    void toEntityCopiesEveryField() {
        OtnLinkCreateRequest request = OtnLinkFixtures.createRequest();
        OtnLink entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getANodeId()).isEqualTo(request.getANodeId());
        assertThat(entity.getZNodeId()).isEqualTo(request.getZNodeId());
        assertThat(entity.getOdu()).isEqualTo(request.getOdu());
        assertThat(entity.getLatencyUs()).isEqualTo(request.getLatencyUs());
        assertThat(entity.getProtection()).isEqualTo(request.getProtection());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
    }

    @Test
    void toResponseCopiesEveryField() {
        OtnLink entity = OtnLinkFixtures.entity();
        OtnLinkResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getANodeId()).isEqualTo(entity.getANodeId());
        assertThat(response.getZNodeId()).isEqualTo(entity.getZNodeId());
        assertThat(response.getOdu()).isEqualTo(entity.getOdu());
        assertThat(response.getLatencyUs()).isEqualTo(entity.getLatencyUs());
        assertThat(response.getProtection()).isEqualTo(entity.getProtection());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
    }

    @Test
    void summaryCarriesIdentity() {
        OtnLink entity = OtnLinkFixtures.entity();
        OtnLinkSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(OtnLinkFixtures.entity())).hasSameSizeAs(OtnLinkMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        OtnLink entity = OtnLinkFixtures.entity();
        OtnLinkUpdateRequest request = new OtnLinkUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setANodeId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setZNodeId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setOdu(OtnLinkOdu.ODU2);
        request.setLatencyUs(new BigDecimal("15.50"));
        request.setProtection("protection-3");
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(OtnLinkLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getANodeId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getZNodeId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getOdu()).isEqualTo(OtnLinkOdu.ODU2);
        assertThat(entity.getLatencyUs()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getProtection()).isEqualTo("protection-3");
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(OtnLinkLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        OtnLink entity = OtnLinkFixtures.entity();
        OtnLink before = OtnLinkFixtures.entity();
        mapper.applyUpdate(new OtnLinkUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
