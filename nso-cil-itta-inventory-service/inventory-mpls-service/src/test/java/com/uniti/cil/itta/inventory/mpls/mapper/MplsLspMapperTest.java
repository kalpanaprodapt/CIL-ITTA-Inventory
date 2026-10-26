package com.uniti.cil.itta.inventory.mpls.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import com.uniti.cil.itta.inventory.mpls.support.MplsLspFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class MplsLspMapperTest {

    private final MplsLspMapper mapper = new MplsLspMapper();

    @Test
    void toEntityCopiesEveryField() {
        MplsLspCreateRequest request = MplsLspFixtures.createRequest();
        MplsLsp entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getLspName()).isEqualTo(request.getLspName());
        assertThat(entity.getHeadEndId()).isEqualTo(request.getHeadEndId());
        assertThat(entity.getTailEndId()).isEqualTo(request.getTailEndId());
        assertThat(entity.getBandwidthMbps()).isEqualTo(request.getBandwidthMbps());
        assertThat(entity.getSetupPriority()).isEqualTo(request.getSetupPriority());
        assertThat(entity.getPathType()).isEqualTo(request.getPathType());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        MplsLsp entity = MplsLspFixtures.entity();
        MplsLspResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getLspName()).isEqualTo(entity.getLspName());
        assertThat(response.getHeadEndId()).isEqualTo(entity.getHeadEndId());
        assertThat(response.getTailEndId()).isEqualTo(entity.getTailEndId());
        assertThat(response.getBandwidthMbps()).isEqualTo(entity.getBandwidthMbps());
        assertThat(response.getSetupPriority()).isEqualTo(entity.getSetupPriority());
        assertThat(response.getPathType()).isEqualTo(entity.getPathType());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        MplsLsp entity = MplsLspFixtures.entity();
        MplsLspSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(MplsLspFixtures.entity())).hasSameSizeAs(MplsLspMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        MplsLsp entity = MplsLspFixtures.entity();
        MplsLspUpdateRequest request = new MplsLspUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setLspName("lspName-3");
        request.setHeadEndId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setTailEndId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setBandwidthMbps(1003L);
        request.setSetupPriority(13);
        request.setPathType(MplsLspPathType.STANDBY);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(MplsLspCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getLspName()).isEqualTo("lspName-3");
        assertThat(entity.getHeadEndId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getTailEndId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getBandwidthMbps()).isEqualTo(1003L);
        assertThat(entity.getSetupPriority()).isEqualTo(13);
        assertThat(entity.getPathType()).isEqualTo(MplsLspPathType.STANDBY);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(MplsLspCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        MplsLsp entity = MplsLspFixtures.entity();
        MplsLsp before = MplsLspFixtures.entity();
        mapper.applyUpdate(new MplsLspUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
