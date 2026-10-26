package com.uniti.cil.itta.inventory.ipam.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import com.uniti.cil.itta.inventory.ipam.support.IpPoolFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class IpPoolMapperTest {

    private final IpPoolMapper mapper = new IpPoolMapper();

    @Test
    void toEntityCopiesEveryField() {
        IpPoolCreateRequest request = IpPoolFixtures.createRequest();
        IpPool entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getSubnetId()).isEqualTo(request.getSubnetId());
        assertThat(entity.getRangeStart()).isEqualTo(request.getRangeStart());
        assertThat(entity.getRangeEnd()).isEqualTo(request.getRangeEnd());
        assertThat(entity.getPoolType()).isEqualTo(request.getPoolType());
        assertThat(entity.getLeaseSeconds()).isEqualTo(request.getLeaseSeconds());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
    }

    @Test
    void toResponseCopiesEveryField() {
        IpPool entity = IpPoolFixtures.entity();
        IpPoolResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getSubnetId()).isEqualTo(entity.getSubnetId());
        assertThat(response.getRangeStart()).isEqualTo(entity.getRangeStart());
        assertThat(response.getRangeEnd()).isEqualTo(entity.getRangeEnd());
        assertThat(response.getPoolType()).isEqualTo(entity.getPoolType());
        assertThat(response.getLeaseSeconds()).isEqualTo(entity.getLeaseSeconds());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
    }

    @Test
    void summaryCarriesIdentity() {
        IpPool entity = IpPoolFixtures.entity();
        IpPoolSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(IpPoolFixtures.entity())).hasSameSizeAs(IpPoolMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        IpPool entity = IpPoolFixtures.entity();
        IpPoolUpdateRequest request = new IpPoolUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setSubnetId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setRangeStart("rangeStart-3");
        request.setRangeEnd("rangeEnd-3");
        request.setPoolType(IpPoolPoolType.NAT);
        request.setLeaseSeconds(13);
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(IpPoolLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getSubnetId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getRangeStart()).isEqualTo("rangeStart-3");
        assertThat(entity.getRangeEnd()).isEqualTo("rangeEnd-3");
        assertThat(entity.getPoolType()).isEqualTo(IpPoolPoolType.NAT);
        assertThat(entity.getLeaseSeconds()).isEqualTo(13);
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(IpPoolLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        IpPool entity = IpPoolFixtures.entity();
        IpPool before = IpPoolFixtures.entity();
        mapper.applyUpdate(new IpPoolUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
