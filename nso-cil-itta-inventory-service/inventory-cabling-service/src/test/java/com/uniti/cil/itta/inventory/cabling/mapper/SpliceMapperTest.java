package com.uniti.cil.itta.inventory.cabling.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.cabling.dto.*;
import com.uniti.cil.itta.inventory.cabling.model.*;
import com.uniti.cil.itta.inventory.cabling.support.SpliceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SpliceMapperTest {

    private final SpliceMapper mapper = new SpliceMapper();

    @Test
    void toEntityCopiesEveryField() {
        SpliceCreateRequest request = SpliceFixtures.createRequest();
        Splice entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getEnclosureId()).isEqualTo(request.getEnclosureId());
        assertThat(entity.getAStrandId()).isEqualTo(request.getAStrandId());
        assertThat(entity.getZStrandId()).isEqualTo(request.getZStrandId());
        assertThat(entity.getLossDb()).isEqualTo(request.getLossDb());
        assertThat(entity.getSplicedOn()).isEqualTo(request.getSplicedOn());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        Splice entity = SpliceFixtures.entity();
        SpliceResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getEnclosureId()).isEqualTo(entity.getEnclosureId());
        assertThat(response.getAStrandId()).isEqualTo(entity.getAStrandId());
        assertThat(response.getZStrandId()).isEqualTo(entity.getZStrandId());
        assertThat(response.getLossDb()).isEqualTo(entity.getLossDb());
        assertThat(response.getSplicedOn()).isEqualTo(entity.getSplicedOn());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        Splice entity = SpliceFixtures.entity();
        SpliceSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(SpliceFixtures.entity())).hasSameSizeAs(SpliceMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Splice entity = SpliceFixtures.entity();
        SpliceUpdateRequest request = new SpliceUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setEnclosureId("enclosureId-3");
        request.setAStrandId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setZStrandId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setLossDb(new BigDecimal("15.50"));
        request.setSplicedOn(LocalDate.of(2026, 4, 13));
        request.setLifecycleStage(SpliceLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(SpliceCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getEnclosureId()).isEqualTo("enclosureId-3");
        assertThat(entity.getAStrandId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getZStrandId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getLossDb()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getSplicedOn()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLifecycleStage()).isEqualTo(SpliceLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(SpliceCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Splice entity = SpliceFixtures.entity();
        Splice before = SpliceFixtures.entity();
        mapper.applyUpdate(new SpliceUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
