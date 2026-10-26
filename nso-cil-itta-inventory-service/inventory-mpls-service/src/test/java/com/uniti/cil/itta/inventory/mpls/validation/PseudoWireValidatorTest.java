package com.uniti.cil.itta.inventory.mpls.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.mpls.model.*;
import com.uniti.cil.itta.inventory.mpls.support.PseudoWireFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PseudoWireValidatorTest {

    private final PseudoWireValidator validator = new PseudoWireValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(PseudoWireFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        PseudoWire entity = PseudoWireFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        PseudoWire entity = PseudoWireFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        PseudoWire entity = PseudoWireFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        PseudoWire entity = PseudoWireFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
