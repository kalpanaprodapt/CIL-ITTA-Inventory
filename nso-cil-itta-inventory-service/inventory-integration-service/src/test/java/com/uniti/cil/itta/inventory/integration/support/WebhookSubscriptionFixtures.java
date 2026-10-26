package com.uniti.cil.itta.inventory.integration.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.integration.dto.*;
import com.uniti.cil.itta.inventory.integration.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for WebhookSubscription. */
public final class WebhookSubscriptionFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private WebhookSubscriptionFixtures() {
    }

    public static WebhookSubscriptionCreateRequest createRequest() {
        WebhookSubscriptionCreateRequest request = new WebhookSubscriptionCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setCallbackUrl("callbackUrl-1");
        request.setEventTypes("eventTypes-1");
        request.setSecretRef("secretRef-1");
        request.setActive(Boolean.TRUE);
        request.setRetryLimit(11);
        request.setLabels("labels-1");
        request.setLifecycleStage(WebhookSubscriptionLifecycleStage.PLAN);
        request.setSourceSystem("sourceSystem-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCode("WEBHOO-0001");
        request.setName("Webhook Subscription one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static WebhookSubscription entity() {
        WebhookSubscription entity = new WebhookSubscription();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setCallbackUrl("callbackUrl-2");
        entity.setEventTypes("eventTypes-2");
        entity.setSecretRef("secretRef-2");
        entity.setActive(Boolean.FALSE);
        entity.setRetryLimit(12);
        entity.setLabels("labels-2");
        entity.setLifecycleStage(WebhookSubscriptionLifecycleStage.BUILD);
        entity.setSourceSystem("sourceSystem-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCode("WEBHOO-0002");
        entity.setName("Webhook Subscription two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static WebhookSubscriptionResponse response() {
        WebhookSubscriptionResponse response = new WebhookSubscriptionResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("WEBHOO-0001");
        response.setName("Webhook Subscription one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static WebhookSubscriptionSearchCriteria criteria() {
        WebhookSubscriptionSearchCriteria criteria = new WebhookSubscriptionSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
