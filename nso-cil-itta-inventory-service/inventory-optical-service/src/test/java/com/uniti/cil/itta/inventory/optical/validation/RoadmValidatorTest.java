package com.uniti.cil.itta.inventory.optical.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.optical.model.*;
import com.uniti.cil.itta.inventory.optical.support.RoadmFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RoadmValidatorTest {

    private final RoadmValidator validator = new RoadmValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(RoadmFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        Roadm entity = RoadmFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        Roadm entity = RoadmFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        Roadm entity = RoadmFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        Roadm entity = RoadmFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
