package com.uniti.cil.itta.inventory.software.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.software.model.*;
import com.uniti.cil.itta.inventory.software.support.LicensePoolFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class LicensePoolValidatorTest {

    private final LicensePoolValidator validator = new LicensePoolValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(LicensePoolFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        LicensePool entity = LicensePoolFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        LicensePool entity = LicensePoolFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        LicensePool entity = LicensePoolFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        LicensePool entity = LicensePoolFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
