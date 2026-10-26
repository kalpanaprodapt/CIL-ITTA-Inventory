package com.uniti.cil.itta.inventory.ipam.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.ipam.dto.*;
import com.uniti.cil.itta.inventory.ipam.model.*;
import com.uniti.cil.itta.inventory.ipam.support.DnsRecordFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DnsRecordMapperTest {

    private final DnsRecordMapper mapper = new DnsRecordMapper();

    @Test
    void toEntityCopiesEveryField() {
        DnsRecordCreateRequest request = DnsRecordFixtures.createRequest();
        DnsRecord entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getZoneName()).isEqualTo(request.getZoneName());
        assertThat(entity.getRecordType()).isEqualTo(request.getRecordType());
        assertThat(entity.getRecordValue()).isEqualTo(request.getRecordValue());
        assertThat(entity.getTtl()).isEqualTo(request.getTtl());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        DnsRecord entity = DnsRecordFixtures.entity();
        DnsRecordResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getZoneName()).isEqualTo(entity.getZoneName());
        assertThat(response.getRecordType()).isEqualTo(entity.getRecordType());
        assertThat(response.getRecordValue()).isEqualTo(entity.getRecordValue());
        assertThat(response.getTtl()).isEqualTo(entity.getTtl());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        DnsRecord entity = DnsRecordFixtures.entity();
        DnsRecordSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(DnsRecordFixtures.entity())).hasSameSizeAs(DnsRecordMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        DnsRecord entity = DnsRecordFixtures.entity();
        DnsRecordUpdateRequest request = new DnsRecordUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setZoneName("zoneName-3");
        request.setRecordType(DnsRecordRecordType.CNAME);
        request.setRecordValue("recordValue-3");
        request.setTtl(13);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(DnsRecordCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getZoneName()).isEqualTo("zoneName-3");
        assertThat(entity.getRecordType()).isEqualTo(DnsRecordRecordType.CNAME);
        assertThat(entity.getRecordValue()).isEqualTo("recordValue-3");
        assertThat(entity.getTtl()).isEqualTo(13);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(DnsRecordCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        DnsRecord entity = DnsRecordFixtures.entity();
        DnsRecord before = DnsRecordFixtures.entity();
        mapper.applyUpdate(new DnsRecordUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
