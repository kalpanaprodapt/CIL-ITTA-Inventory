package com.uniti.cil.itta.inventory.switching.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.switching.model.*;
import com.uniti.cil.itta.inventory.switching.support.RouteTargetFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class RouteTargetValidatorTest {

    private final RouteTargetValidator validator = new RouteTargetValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(RouteTargetFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        RouteTarget entity = RouteTargetFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        RouteTarget entity = RouteTargetFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        RouteTarget entity = RouteTargetFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        RouteTarget entity = RouteTargetFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
