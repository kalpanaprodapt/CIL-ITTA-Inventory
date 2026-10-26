package com.uniti.cil.itta.inventory.circuit.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.circuit.model.*;
import com.uniti.cil.itta.inventory.circuit.support.CircuitProviderFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class CircuitProviderValidatorTest {

    private final CircuitProviderValidator validator = new CircuitProviderValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(CircuitProviderFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        CircuitProvider entity = CircuitProviderFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
