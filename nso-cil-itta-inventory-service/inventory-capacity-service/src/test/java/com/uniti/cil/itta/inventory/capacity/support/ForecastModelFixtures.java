package com.uniti.cil.itta.inventory.capacity.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.capacity.dto.*;
import com.uniti.cil.itta.inventory.capacity.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for ForecastModel. */
public final class ForecastModelFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private ForecastModelFixtures() {
    }

    public static ForecastModelCreateRequest createRequest() {
        ForecastModelCreateRequest request = new ForecastModelCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setModelName("modelName-1");
        request.setAlgorithm(ForecastModelAlgorithm.LINEAR);
        request.setTrainedOn(Instant.parse("2026-02-11T10:15:30Z"));
        request.setMape(new BigDecimal("13.50"));
        request.setCriticality(ForecastModelCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setEffectiveFrom(LocalDate.of(2026, 2, 11));
        request.setEffectiveTo(LocalDate.of(2026, 2, 11));
        request.setCode("FORECA-0001");
        request.setName("Forecast Model one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static ForecastModel entity() {
        ForecastModel entity = new ForecastModel();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setModelName("modelName-2");
        entity.setAlgorithm(ForecastModelAlgorithm.HOLT_WINTERS);
        entity.setTrainedOn(Instant.parse("2026-03-12T10:15:30Z"));
        entity.setMape(new BigDecimal("14.50"));
        entity.setCriticality(ForecastModelCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setEffectiveFrom(LocalDate.of(2026, 3, 12));
        entity.setEffectiveTo(LocalDate.of(2026, 3, 12));
        entity.setCode("FORECA-0002");
        entity.setName("Forecast Model two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static ForecastModelResponse response() {
        ForecastModelResponse response = new ForecastModelResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("FORECA-0001");
        response.setName("Forecast Model one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static ForecastModelSearchCriteria criteria() {
        ForecastModelSearchCriteria criteria = new ForecastModelSearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
