package com.uniti.cil.itta.inventory.geography.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.geography.dto.*;
import com.uniti.cil.itta.inventory.geography.model.*;
import com.uniti.cil.itta.inventory.geography.support.CountryFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CountryMapperTest {

    private final CountryMapper mapper = new CountryMapper();

    @Test
    void toEntityCopiesEveryField() {
        CountryCreateRequest request = CountryFixtures.createRequest();
        Country entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getIsoCode()).isEqualTo(request.getIsoCode());
        assertThat(entity.getIsoNumeric()).isEqualTo(request.getIsoNumeric());
        assertThat(entity.getDialCode()).isEqualTo(request.getDialCode());
        assertThat(entity.getCurrency()).isEqualTo(request.getCurrency());
        assertThat(entity.getRegulatoryBody()).isEqualTo(request.getRegulatoryBody());
        assertThat(entity.getTimezone()).isEqualTo(request.getTimezone());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
        assertThat(entity.getCriticality()).isEqualTo(request.getCriticality());
        assertThat(entity.getCostCenter()).isEqualTo(request.getCostCenter());
        assertThat(entity.getNotes()).isEqualTo(request.getNotes());
    }

    @Test
    void toResponseCopiesEveryField() {
        Country entity = CountryFixtures.entity();
        CountryResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getIsoCode()).isEqualTo(entity.getIsoCode());
        assertThat(response.getIsoNumeric()).isEqualTo(entity.getIsoNumeric());
        assertThat(response.getDialCode()).isEqualTo(entity.getDialCode());
        assertThat(response.getCurrency()).isEqualTo(entity.getCurrency());
        assertThat(response.getRegulatoryBody()).isEqualTo(entity.getRegulatoryBody());
        assertThat(response.getTimezone()).isEqualTo(entity.getTimezone());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
        assertThat(response.getCriticality()).isEqualTo(entity.getCriticality());
        assertThat(response.getCostCenter()).isEqualTo(entity.getCostCenter());
        assertThat(response.getNotes()).isEqualTo(entity.getNotes());
    }

    @Test
    void summaryCarriesIdentity() {
        Country entity = CountryFixtures.entity();
        CountrySummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CountryFixtures.entity())).hasSameSizeAs(CountryMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        Country entity = CountryFixtures.entity();
        CountryUpdateRequest request = new CountryUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setIsoCode("isoCode-3");
        request.setIsoNumeric(13);
        request.setDialCode("dialCode-3");
        request.setCurrency("currency-3");
        request.setRegulatoryBody("regulatoryBody-3");
        request.setTimezone("timezone-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        request.setCriticality(CountryCriticality.HIGH);
        request.setCostCenter("costCenter-3");
        request.setNotes("notes-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getIsoCode()).isEqualTo("isoCode-3");
        assertThat(entity.getIsoNumeric()).isEqualTo(13);
        assertThat(entity.getDialCode()).isEqualTo("dialCode-3");
        assertThat(entity.getCurrency()).isEqualTo("currency-3");
        assertThat(entity.getRegulatoryBody()).isEqualTo("regulatoryBody-3");
        assertThat(entity.getTimezone()).isEqualTo("timezone-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
        assertThat(entity.getCriticality()).isEqualTo(CountryCriticality.HIGH);
        assertThat(entity.getCostCenter()).isEqualTo("costCenter-3");
        assertThat(entity.getNotes()).isEqualTo("notes-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        Country entity = CountryFixtures.entity();
        Country before = CountryFixtures.entity();
        mapper.applyUpdate(new CountryUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
