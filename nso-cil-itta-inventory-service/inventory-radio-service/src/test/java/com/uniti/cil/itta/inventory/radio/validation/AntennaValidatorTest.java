package com.uniti.cil.itta.inventory.radio.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.radio.model.*;
import com.uniti.cil.itta.inventory.radio.support.AntennaFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AntennaValidatorTest {

    private final AntennaValidator validator = new AntennaValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(AntennaFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        Antenna entity = AntennaFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        Antenna entity = AntennaFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        Antenna entity = AntennaFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        Antenna entity = AntennaFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
