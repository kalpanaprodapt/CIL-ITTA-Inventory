package com.uniti.cil.itta.inventory.radio.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.radio.model.*;
import com.uniti.cil.itta.inventory.radio.support.RadioUnitFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RadioUnitValidatorTest {

    private final RadioUnitValidator validator = new RadioUnitValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(RadioUnitFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        RadioUnit entity = RadioUnitFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        RadioUnit entity = RadioUnitFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        RadioUnit entity = RadioUnitFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        RadioUnit entity = RadioUnitFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
