package com.uniti.cil.itta.inventory.mpls.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.mpls.dto.*;
import com.uniti.cil.itta.inventory.mpls.model.*;
import com.uniti.cil.itta.inventory.mpls.support.QosPolicyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class QosPolicyMapperTest {

    private final QosPolicyMapper mapper = new QosPolicyMapper();

    @Test
    void toEntityCopiesEveryField() {
        QosPolicyCreateRequest request = QosPolicyFixtures.createRequest();
        QosPolicy entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getPolicyName()).isEqualTo(request.getPolicyName());
        assertThat(entity.getClassCount()).isEqualTo(request.getClassCount());
        assertThat(entity.getCirMbps()).isEqualTo(request.getCirMbps());
        assertThat(entity.getPirMbps()).isEqualTo(request.getPirMbps());
        assertThat(entity.getMarkingScheme()).isEqualTo(request.getMarkingScheme());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        QosPolicy entity = QosPolicyFixtures.entity();
        QosPolicyResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getPolicyName()).isEqualTo(entity.getPolicyName());
        assertThat(response.getClassCount()).isEqualTo(entity.getClassCount());
        assertThat(response.getCirMbps()).isEqualTo(entity.getCirMbps());
        assertThat(response.getPirMbps()).isEqualTo(entity.getPirMbps());
        assertThat(response.getMarkingScheme()).isEqualTo(entity.getMarkingScheme());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        QosPolicy entity = QosPolicyFixtures.entity();
        QosPolicySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(QosPolicyFixtures.entity())).hasSameSizeAs(QosPolicyMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        QosPolicy entity = QosPolicyFixtures.entity();
        QosPolicyUpdateRequest request = new QosPolicyUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setPolicyName("policyName-3");
        request.setClassCount(13);
        request.setCirMbps(1003L);
        request.setPirMbps(1003L);
        request.setMarkingScheme("markingScheme-3");
        request.setLabels("labels-3");
        request.setLifecycleStage(QosPolicyLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getPolicyName()).isEqualTo("policyName-3");
        assertThat(entity.getClassCount()).isEqualTo(13);
        assertThat(entity.getCirMbps()).isEqualTo(1003L);
        assertThat(entity.getPirMbps()).isEqualTo(1003L);
        assertThat(entity.getMarkingScheme()).isEqualTo("markingScheme-3");
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(QosPolicyLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        QosPolicy entity = QosPolicyFixtures.entity();
        QosPolicy before = QosPolicyFixtures.entity();
        mapper.applyUpdate(new QosPolicyUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
