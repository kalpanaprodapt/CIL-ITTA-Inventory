package com.uniti.cil.itta.inventory.capacity.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.capacity.model.*;
import com.uniti.cil.itta.inventory.capacity.support.ThresholdPolicyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ThresholdPolicyValidatorTest {

    private final ThresholdPolicyValidator validator = new ThresholdPolicyValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(ThresholdPolicyFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        ThresholdPolicy entity = ThresholdPolicyFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
