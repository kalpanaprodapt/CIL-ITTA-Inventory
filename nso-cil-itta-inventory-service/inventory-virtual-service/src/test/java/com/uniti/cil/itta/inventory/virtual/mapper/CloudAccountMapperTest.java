package com.uniti.cil.itta.inventory.virtual.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import com.uniti.cil.itta.inventory.virtual.support.CloudAccountFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CloudAccountMapperTest {

    private final CloudAccountMapper mapper = new CloudAccountMapper();

    @Test
    void toEntityCopiesEveryField() {
        CloudAccountCreateRequest request = CloudAccountFixtures.createRequest();
        CloudAccount entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getProvider()).isEqualTo(request.getProvider());
        assertThat(entity.getAccountRef()).isEqualTo(request.getAccountRef());
        assertThat(entity.getSubscriptionName()).isEqualTo(request.getSubscriptionName());
        assertThat(entity.getMonthlyBudget()).isEqualTo(request.getMonthlyBudget());
        assertThat(entity.getEnvironment()).isEqualTo(request.getEnvironment());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
        assertThat(entity.getEffectiveFrom()).isEqualTo(request.getEffectiveFrom());
        assertThat(entity.getEffectiveTo()).isEqualTo(request.getEffectiveTo());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
    }

    @Test
    void toResponseCopiesEveryField() {
        CloudAccount entity = CloudAccountFixtures.entity();
        CloudAccountResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getProvider()).isEqualTo(entity.getProvider());
        assertThat(response.getAccountRef()).isEqualTo(entity.getAccountRef());
        assertThat(response.getSubscriptionName()).isEqualTo(entity.getSubscriptionName());
        assertThat(response.getMonthlyBudget()).isEqualTo(entity.getMonthlyBudget());
        assertThat(response.getEnvironment()).isEqualTo(entity.getEnvironment());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
        assertThat(response.getEffectiveFrom()).isEqualTo(entity.getEffectiveFrom());
        assertThat(response.getEffectiveTo()).isEqualTo(entity.getEffectiveTo());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
    }

    @Test
    void summaryCarriesIdentity() {
        CloudAccount entity = CloudAccountFixtures.entity();
        CloudAccountSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CloudAccountFixtures.entity())).hasSameSizeAs(CloudAccountMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        CloudAccount entity = CloudAccountFixtures.entity();
        CloudAccountUpdateRequest request = new CloudAccountUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setProvider(CloudAccountProvider.GCP);
        request.setAccountRef("accountRef-3");
        request.setSubscriptionName("subscriptionName-3");
        request.setMonthlyBudget(new BigDecimal("15.50"));
        request.setEnvironment("environment-3");
        request.setNotes("notes-3");
        request.setEffectiveFrom(LocalDate.of(2026, 4, 13));
        request.setEffectiveTo(LocalDate.of(2026, 4, 13));
        request.setLabels("labels-3");
        request.setLifecycleStage(CloudAccountLifecycleStage.OPERATE);
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getProvider()).isEqualTo(CloudAccountProvider.GCP);
        assertThat(entity.getAccountRef()).isEqualTo("accountRef-3");
        assertThat(entity.getSubscriptionName()).isEqualTo("subscriptionName-3");
        assertThat(entity.getMonthlyBudget()).isEqualTo(new BigDecimal("15.50"));
        assertThat(entity.getEnvironment()).isEqualTo("environment-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
        assertThat(entity.getEffectiveFrom()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getEffectiveTo()).isEqualTo(LocalDate.of(2026, 4, 13));
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(CloudAccountLifecycleStage.OPERATE);
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        CloudAccount entity = CloudAccountFixtures.entity();
        CloudAccount before = CloudAccountFixtures.entity();
        mapper.applyUpdate(new CloudAccountUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
