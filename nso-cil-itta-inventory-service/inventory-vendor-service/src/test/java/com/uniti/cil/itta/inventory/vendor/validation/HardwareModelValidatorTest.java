package com.uniti.cil.itta.inventory.vendor.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.vendor.model.*;
import com.uniti.cil.itta.inventory.vendor.support.HardwareModelFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class HardwareModelValidatorTest {

    private final HardwareModelValidator validator = new HardwareModelValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(HardwareModelFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        HardwareModel entity = HardwareModelFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        HardwareModel entity = HardwareModelFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        HardwareModel entity = HardwareModelFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        HardwareModel entity = HardwareModelFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
