package com.uniti.cil.itta.inventory.vendor.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.vendor.model.*;
import com.uniti.cil.itta.inventory.vendor.support.WarrantyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class WarrantyValidatorTest {

    private final WarrantyValidator validator = new WarrantyValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(WarrantyFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        Warranty entity = WarrantyFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        Warranty entity = WarrantyFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        Warranty entity = WarrantyFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        Warranty entity = WarrantyFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
