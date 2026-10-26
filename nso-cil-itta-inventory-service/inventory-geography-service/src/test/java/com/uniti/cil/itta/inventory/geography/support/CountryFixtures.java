package com.uniti.cil.itta.inventory.geography.support;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;

/** Test data builders for Country. */
public final class CountryFixtures {

    public static final String TENANT = "tenant-a";
    public static final UUID ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private CountryFixtures() {
    }

    public static CountryCreateRequest createRequest() {
        CountryCreateRequest request = new CountryCreateRequest();
        request.setCode("code-1");
        request.setName("name-1");
        request.setDescription("description-1");
        request.setStatus(LifecycleStatus.PLANNED);
        request.setExternalRef("externalRef-1");
        request.setIsoCode("isoCode-1");
        request.setIsoNumeric(11);
        request.setDialCode("dialCode-1");
        request.setCurrency("currency-1");
        request.setRegulatoryBody("regulatoryBody-1");
        request.setTimezone("timezone-1");
        request.setRegion("region-1");
        request.setOwner("owner-1");
        request.setCriticality(CountryCriticality.LOW);
        request.setCostCenter("costCenter-1");
        request.setNotes("notes-1");
        request.setCode("COUNTR-0001");
        request.setName("Country one");
        request.setStatus(LifecycleStatus.ACTIVE);
        return request;
    }

    public static Country entity() {
        Country entity = new Country();
        entity.setCode("code-2");
        entity.setName("name-2");
        entity.setDescription("description-2");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setExternalRef("externalRef-2");
        entity.setIsoCode("isoCode-2");
        entity.setIsoNumeric(12);
        entity.setDialCode("dialCode-2");
        entity.setCurrency("currency-2");
        entity.setRegulatoryBody("regulatoryBody-2");
        entity.setTimezone("timezone-2");
        entity.setRegion("region-2");
        entity.setOwner("owner-2");
        entity.setCriticality(CountryCriticality.MEDIUM);
        entity.setCostCenter("costCenter-2");
        entity.setNotes("notes-2");
        entity.setCode("COUNTR-0002");
        entity.setName("Country two");
        entity.setStatus(LifecycleStatus.ACTIVE);
        entity.setTenantId(TENANT);
        return entity;
    }

    public static CountryResponse response() {
        CountryResponse response = new CountryResponse();
        response.setId(ID);
        response.setTenantId(TENANT);
        response.setVersion(0L);
        response.setCode("COUNTR-0001");
        response.setName("Country one");
        response.setStatus(LifecycleStatus.ACTIVE);
        return response;
    }

    public static CountrySearchCriteria criteria() {
        CountrySearchCriteria criteria = new CountrySearchCriteria();
        criteria.setQ("one");
        criteria.setStatus(List.of(LifecycleStatus.ACTIVE));
        return criteria;
    }
}
