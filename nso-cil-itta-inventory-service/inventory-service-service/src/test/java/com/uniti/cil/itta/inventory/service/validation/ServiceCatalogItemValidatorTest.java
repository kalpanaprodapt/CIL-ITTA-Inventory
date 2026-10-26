package com.uniti.cil.itta.inventory.service.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.service.model.*;
import com.uniti.cil.itta.inventory.service.support.ServiceCatalogItemFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceCatalogItemValidatorTest {

    private final ServiceCatalogItemValidator validator = new ServiceCatalogItemValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(ServiceCatalogItemFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        ServiceCatalogItem entity = ServiceCatalogItemFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
