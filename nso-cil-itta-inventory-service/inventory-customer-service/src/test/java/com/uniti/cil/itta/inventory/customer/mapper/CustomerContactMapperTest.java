package com.uniti.cil.itta.inventory.customer.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.uniti.cil.itta.inventory.common.model.LifecycleStatus;
import com.uniti.cil.itta.inventory.customer.dto.*;
import com.uniti.cil.itta.inventory.customer.model.*;
import com.uniti.cil.itta.inventory.customer.support.CustomerContactFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CustomerContactMapperTest {

    private final CustomerContactMapper mapper = new CustomerContactMapper();

    @Test
    void toEntityCopiesEveryField() {
        CustomerContactCreateRequest request = CustomerContactFixtures.createRequest();
        CustomerContact entity = mapper.toEntity(request);
        assertThat(entity.getCode()).isEqualTo(request.getCode());
        assertThat(entity.getName()).isEqualTo(request.getName());
        assertThat(entity.getDescription()).isEqualTo(request.getDescription());
        assertThat(entity.getStatus()).isEqualTo(request.getStatus());
        assertThat(entity.getExternalRef()).isEqualTo(request.getExternalRef());
        assertThat(entity.getCustomerId()).isEqualTo(request.getCustomerId());
        assertThat(entity.getFullName()).isEqualTo(request.getFullName());
        assertThat(entity.getEmail()).isEqualTo(request.getEmail());
        assertThat(entity.getPhone()).isEqualTo(request.getPhone());
        assertThat(entity.getContactRole()).isEqualTo(request.getContactRole());
        assertThat(entity.getLabels()).isEqualTo(request.getLabels());
        assertThat(entity.getLifecycleStage()).isEqualTo(request.getLifecycleStage());
        assertThat(entity.getSourceSystem()).isEqualTo(request.getSourceSystem());
        assertThat(entity.getRegion()).isEqualTo(request.getRegion());
        assertThat(entity.getOwner()).isEqualTo(request.getOwner());
    }

    @Test
    void toResponseCopiesEveryField() {
        CustomerContact entity = CustomerContactFixtures.entity();
        CustomerContactResponse response = mapper.toResponse(entity);
        assertThat(response.getCode()).isEqualTo(entity.getCode());
        assertThat(response.getName()).isEqualTo(entity.getName());
        assertThat(response.getDescription()).isEqualTo(entity.getDescription());
        assertThat(response.getStatus()).isEqualTo(entity.getStatus());
        assertThat(response.getExternalRef()).isEqualTo(entity.getExternalRef());
        assertThat(response.getCustomerId()).isEqualTo(entity.getCustomerId());
        assertThat(response.getFullName()).isEqualTo(entity.getFullName());
        assertThat(response.getEmail()).isEqualTo(entity.getEmail());
        assertThat(response.getPhone()).isEqualTo(entity.getPhone());
        assertThat(response.getContactRole()).isEqualTo(entity.getContactRole());
        assertThat(response.getLabels()).isEqualTo(entity.getLabels());
        assertThat(response.getLifecycleStage()).isEqualTo(entity.getLifecycleStage());
        assertThat(response.getSourceSystem()).isEqualTo(entity.getSourceSystem());
        assertThat(response.getRegion()).isEqualTo(entity.getRegion());
        assertThat(response.getOwner()).isEqualTo(entity.getOwner());
    }

    @Test
    void summaryCarriesIdentity() {
        CustomerContact entity = CustomerContactFixtures.entity();
        CustomerContactSummary summary = mapper.toSummary(entity);
        assertThat(summary.getCode()).isEqualTo(entity.getCode());
        assertThat(summary.getName()).isEqualTo(entity.getName());
        assertThat(summary.getStatus()).isEqualTo(LifecycleStatus.ACTIVE);
    }

    @Test
    void csvRowMatchesHeaderWidth() {
        assertThat(mapper.toCsvRow(CustomerContactFixtures.entity())).hasSameSizeAs(CustomerContactMapper.CSV_HEADER);
    }

    @Test
    void applyUpdateChangesEveryProvidedField() {
        CustomerContact entity = CustomerContactFixtures.entity();
        CustomerContactUpdateRequest request = new CustomerContactUpdateRequest();
        request.setCode("code-3");
        request.setName("name-3");
        request.setDescription("description-3");
        request.setStatus(LifecycleStatus.MAINTENANCE);
        request.setExternalRef("externalRef-3");
        request.setCustomerId(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        request.setFullName("fullName-3");
        request.setEmail("email-3");
        request.setPhone("phone-3");
        request.setContactRole(CustomerContactContactRole.ESCALATION);
        request.setLabels("labels-3");
        request.setLifecycleStage(CustomerContactLifecycleStage.OPERATE);
        request.setSourceSystem("sourceSystem-3");
        request.setRegion("region-3");
        request.setOwner("owner-3");
        mapper.applyUpdate(request, entity);
        assertThat(entity.getCode()).isEqualTo("code-3");
        assertThat(entity.getName()).isEqualTo("name-3");
        assertThat(entity.getDescription()).isEqualTo("description-3");
        assertThat(entity.getStatus()).isEqualTo(LifecycleStatus.MAINTENANCE);
        assertThat(entity.getExternalRef()).isEqualTo("externalRef-3");
        assertThat(entity.getCustomerId()).isEqualTo(UUID.fromString("00000000-0000-0000-0000-000000000013"));
        assertThat(entity.getFullName()).isEqualTo("fullName-3");
        assertThat(entity.getEmail()).isEqualTo("email-3");
        assertThat(entity.getPhone()).isEqualTo("phone-3");
        assertThat(entity.getContactRole()).isEqualTo(CustomerContactContactRole.ESCALATION);
        assertThat(entity.getLabels()).isEqualTo("labels-3");
        assertThat(entity.getLifecycleStage()).isEqualTo(CustomerContactLifecycleStage.OPERATE);
        assertThat(entity.getSourceSystem()).isEqualTo("sourceSystem-3");
        assertThat(entity.getRegion()).isEqualTo("region-3");
        assertThat(entity.getOwner()).isEqualTo("owner-3");
    }

    @Test
    void applyUpdateKeepsFieldsWhenRequestIsEmpty() {
        CustomerContact entity = CustomerContactFixtures.entity();
        CustomerContact before = CustomerContactFixtures.entity();
        mapper.applyUpdate(new CustomerContactUpdateRequest(), entity);
        assertThat(entity.toString()).isEqualTo(before.toString());
    }
}
