package com.uniti.cil.itta.inventory.integration.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import com.uniti.cil.itta.inventory.integration.support.WebhookSubscriptionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WebhookSubscriptionMapperTest {

    private final WebhookSubscriptionMapper mapper = new WebhookSubscriptionMapper();

    @Test
    void toEntityCopiesEveryField() {
        WebhookSubscriptionCreateRequest request = WebhookSubscriptionFixtures.createRequest();
        WebhookSubscription entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCallbackUrl()).isEqualTo(request.getCallbackUrl());
        assertThat(entity.getEventTypes()).isEqualTo(request.getEventTypes());
        assertThat(entity.getSecretRef()).isEqualTo(request.getSecretRef());
        assertThat(entity.getActive()).isEqualTo(request.getActive());
        assertThat(entity.getRetryLimit()).isEqualTo(request.getRetryLimit());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        WebhookSubscriptionResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCallbackUrl()).isEqualTo(entity.getCallbackUrl());
        assertThat(response.getEventTypes()).isEqualTo(entity.getEventTypes());
        assertThat(response.getSecretRef()).isEqualTo(entity.getSecretRef());
        assertThat(response.getActive()).isEqualTo(entity.getActive());
        assertThat(response.getRetryLimit()).isEqualTo(entity.getRetryLimit());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        WebhookSubscriptionSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(WebhookSubscriptionFixtures.entity())).hasSameSizeAs(WebhookSubscriptionMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        WebhookSubscriptionUpdateRequest request = new WebhookSubscriptionUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCallbackUrl("callbackUrl-3");
        request.setEventTypes("eventTypes-3");
        request.setSecretRef("secretRef-3");
        request.setActive(Boolean.TRUE);
        request.setRetryLimit(13);
        request.setLabels("labels-3");
        request.setLifecycleStage(WebhookSubscriptionLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCallbackUrl()).isEqualTo("callbackUrl-3");
        assertThat(entity.getEventTypes()).isEqualTo("eventTypes-3");
        assertThat(entity.getSecretRef()).isEqualTo("secretRef-3");
        assertThat(entity.getActive()).isEqualTo(Boolean.TRUE);
        assertThat(entity.getRetryLimit()).isEqualTo(13);
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(WebhookSubscriptionLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        WebhookSubscription entity = WebhookSubscriptionFixtures.entity();
        WebhookSubscription before = WebhookSubscriptionFixtures.entity();
        mapper.applyUpdate(new WebhookSubscriptionUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
