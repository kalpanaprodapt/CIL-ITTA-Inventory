package com.uniti.cil.itta.inventory.virtual.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.virtual.dto.*;
import com.uniti.cil.itta.inventory.virtual.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for CloudAccount. */
public final class CloudAccountFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CloudAccountFixtures() {
    }

    public static CloudAccountCreateRequest createRequest() {
        CloudAccountCreateRequest request = new CloudAccountCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setProvider(CloudAccountProvider.AZURE);
        request.setAccountRef("accountRef-1");
        request.setSubscriptionName("subscriptionName-1");
        request.setMonthlyBudget(new BigDecimal("13.50"));
        request.setEnvironment("environment-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setLabels("labels-1");
        request.setLifecycleStage(CloudAccountLifecycleStage.PLAN);
        request.setCode("CLOUDA-0001");
        request.setName("Cloud Account one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static CloudAccount entity() {
        CloudAccount entity = new CloudAccount();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setProvider(CloudAccountProvider.AWS);
        entity.setAccountRef("accountRef-2");
        entity.setSubscriptionName("subscriptionName-2");
        entity.setMonthlyBudget(new BigDecimal("14.50"));
        entity.setEnvironment("environment-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setLabels("labels-2");
        entity.setLifecycleStage(CloudAccountLifecycleStage.BUILD);
        entity.setCode("CLOUDA-0002");
        entity.setName("Cloud Account two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CloudAccountResponse response() {
        CloudAccountResponse response = new CloudAccountResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("CLOUDA-0001");
        response.setName("Cloud Account one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CloudAccountSearchCriteria criteria() {
        CloudAccountSearchCriteria criteria = new CloudAccountSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
