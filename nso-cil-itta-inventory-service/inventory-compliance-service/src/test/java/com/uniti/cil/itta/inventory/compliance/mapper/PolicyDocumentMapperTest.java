package com.uniti.cil.itta.inventory.compliance.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.compliance.dto.*;
import com.uniti.cil.itta.inventory.compliance.model.*;
import com.uniti.cil.itta.inventory.compliance.support.PolicyDocumentFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PolicyDocumentMapperTest {

    private final PolicyDocumentMapper mapper = new PolicyDocumentMapper();

    @Test
    void toEntityCopiesEveryField() {
        PolicyDocumentCreateRequest request = PolicyDocumentFixtures.createRequest();
        PolicyDocument entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getTitle()).isEqualTo(request.getTitle());
        assertThat(entity.getDocVersion()).isEqualTo(request.getDocVersion());
        assertThat(entity.getEffectiveDate()).isEqualTo(request.getEffectiveDate());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getDocumentUrl()).isEqualTo(request.getDocumentUrl());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
    }

    @Test
    void toResponseCopiesEveryField() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        PolicyDocumentResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getTitle()).isEqualTo(entity.getTitle());
        assertThat(response.getDocVersion()).isEqualTo(entity.getDocVersion());
        assertThat(response.getEffectiveDate()).isEqualTo(entity.getEffectiveDate());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getDocumentUrl()).isEqualTo(entity.getDocumentUrl());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
    }

    @Test
    void summaryCarriesIdentity() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        PolicyDocumentSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(PolicyDocumentFixtures.entity())).hasSameSizeAs(PolicyDocumentMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        PolicyDocumentUpdateRequest request = new PolicyDocumentUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setTitle("title-3");
        request.setDocVersion("docVersion-3");
        request.setEffectiveDate(LocalDate.of(2026, 4, 13));
        request.setOwner("owner-3");
        request.setDocumentUrl("documentUrl-3");
        request.setLifecycleStage(PolicyDocumentLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setCriticality(PolicyDocumentCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getTitle()).isEqualTo("title-3");
        assertThat(entity.getDocVersion()).isEqualTo("docVersion-3");
        assertThat(entity.getEffectiveDate()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getDocumentUrl()).isEqualTo("documentUrl-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(PolicyDocumentLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getCriticality()).isEqualTo(PolicyDocumentCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        PolicyDocument entity = PolicyDocumentFixtures.entity();
        PolicyDocument before = PolicyDocumentFixtures.entity();
        mapper.applyUpdate(new PolicyDocumentUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
