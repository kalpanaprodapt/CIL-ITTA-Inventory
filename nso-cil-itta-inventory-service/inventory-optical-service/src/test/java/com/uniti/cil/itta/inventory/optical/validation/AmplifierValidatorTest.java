package com.uniti.cil.itta.inventory.optical.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.optical.model.*;
import com.uniti.cil.itta.inventory.optical.support.AmplifierFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AmplifierValidatorTest {

    private final AmplifierValidator validator = new AmplifierValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(AmplifierFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        Amplifier entity = AmplifierFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        Amplifier entity = AmplifierFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        Amplifier entity = AmplifierFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        Amplifier entity = AmplifierFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
