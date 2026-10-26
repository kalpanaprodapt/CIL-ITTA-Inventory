package com.uniti.cil.itta.inventory.virtual.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import com.uniti.cil.itta.inventory.virtual.support.K8sClusterFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class K8sClusterMapperTest {

    private final K8sClusterMapper mapper = new K8sClusterMapper();

    @Test
    void toEntityCopiesEveryField() {
        K8sClusterCreateRequest request = K8sClusterFixtures.createRequest();
        K8sCluster entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getClusterName()).isEqualTo(request.getClusterName());
        assertThat(entity.getClusterVersion()).isEqualTo(request.getClusterVersion());
        assertThat(entity.getNodeCount()).isEqualTo(request.getNodeCount());
        assertThat(entity.getCloudAccountId()).isEqualTo(request.getCloudAccountId());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getApiEndpoint()).isEqualTo(request.getApiEndpoint());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        K8sCluster entity = K8sClusterFixtures.entity();
        K8sClusterResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getClusterName()).isEqualTo(entity.getClusterName());
        assertThat(response.getClusterVersion()).isEqualTo(entity.getClusterVersion());
        assertThat(response.getNodeCount()).isEqualTo(entity.getNodeCount());
        assertThat(response.getCloudAccountId()).isEqualTo(entity.getCloudAccountId());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getApiEndpoint()).isEqualTo(entity.getApiEndpoint());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        K8sCluster entity = K8sClusterFixtures.entity();
        K8sClusterSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(K8sClusterFixtures.entity())).hasSameSizeAs(K8sClusterMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        K8sCluster entity = K8sClusterFixtures.entity();
        K8sClusterUpdateRequest request = new K8sClusterUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setClusterName("clusterName-3");
        request.setClusterVersion("clusterVersion-3");
        request.setNodeCount(13);
        request.setCloudAccountId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setRegion("region-3");
        request.setApiEndpoint("apiEndpoint-3");
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(K8sClusterLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getClusterName()).isEqualTo("clusterName-3");
        assertThat(entity.getClusterVersion()).isEqualTo("clusterVersion-3");
        assertThat(entity.getNodeCount()).isEqualTo(13);
        assertThat(entity.getCloudAccountId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getApiEndpoint()).isEqualTo("apiEndpoint-3");
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(K8sClusterLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        K8sCluster entity = K8sClusterFixtures.entity();
        K8sCluster before = K8sClusterFixtures.entity();
        mapper.applyUpdate(new K8sClusterUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
