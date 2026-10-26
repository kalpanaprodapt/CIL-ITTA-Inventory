package com.uniti.cil.itta.inventory.virtual.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import com.uniti.cil.itta.inventory.virtual.support.VnfFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class VnfMapperTest {

    private final VnfMapper mapper = new VnfMapper();

    @Test
    void toEntityCopiesEveryField() {
        VnfCreateRequest request = VnfFixtures.createRequest();
        Vnf entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getVnfName()).isEqualTo(request.getVnfName());
        assertThat(entity.getVnfType()).isEqualTo(request.getVnfType());
        assertThat(entity.getFlavor()).isEqualTo(request.getFlavor());
        assertThat(entity.getVcpu()).isEqualTo(request.getVcpu());
        assertThat(entity.getMemoryGb()).isEqualTo(request.getMemoryGb());
        assertThat(entity.getInstanceCount()).isEqualTo(request.getInstanceCount());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
    }

    @Test
    void toResponseCopiesEveryField() {
        Vnf entity = VnfFixtures.entity();
        VnfResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getVnfName()).isEqualTo(entity.getVnfName());
        assertThat(response.getVnfType()).isEqualTo(entity.getVnfType());
        assertThat(response.getFlavor()).isEqualTo(entity.getFlavor());
        assertThat(response.getVcpu()).isEqualTo(entity.getVcpu());
        assertThat(response.getMemoryGb()).isEqualTo(entity.getMemoryGb());
        assertThat(response.getInstanceCount()).isEqualTo(entity.getInstanceCount());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
    }

    @Test
    void summaryCarriesIdentity() {
        Vnf entity = VnfFixtures.entity();
        VnfSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(VnfFixtures.entity())).hasSameSizeAs(VnfMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Vnf entity = VnfFixtures.entity();
        VnfUpdateRequest request = new VnfUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setVnfName("vnfName-3");
        request.setVnfType("vnfType-3");
        request.setFlavor("flavor-3");
        request.setVcpu(13);
        request.setMemoryGb(13);
        request.setInstanceCount(13);
        request.setLifecycleStage(VnfLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(VnfCriticality.HIGH);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getVnfName()).isEqualTo("vnfName-3");
        assertThat(entity.getVnfType()).isEqualTo("vnfType-3");
        assertThat(entity.getFlavor()).isEqualTo("flavor-3");
        assertThat(entity.getVcpu()).isEqualTo(13);
        assertThat(entity.getMemoryGb()).isEqualTo(13);
        assertThat(entity.getInstanceCount()).isEqualTo(13);
        assertThat(entity.getLifecycleStage()).isEqualTo(VnfLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(VnfCriticality.HIGH);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Vnf entity = VnfFixtures.entity();
        Vnf before = VnfFixtures.entity();
        mapper.applyUpdate(new VnfUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
