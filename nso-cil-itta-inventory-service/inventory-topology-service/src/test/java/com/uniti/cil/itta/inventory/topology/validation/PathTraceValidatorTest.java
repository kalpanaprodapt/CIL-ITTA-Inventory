package com.uniti.cil.itta.inventory.topology.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.topology.model.*;
import com.uniti.cil.itta.inventory.topology.support.PathTraceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PathTraceValidatorTest {

    private final PathTraceValidator validator = new PathTraceValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(PathTraceFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        PathTrace entity = PathTraceFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        PathTrace entity = PathTraceFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        PathTrace entity = PathTraceFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        PathTrace entity = PathTraceFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
