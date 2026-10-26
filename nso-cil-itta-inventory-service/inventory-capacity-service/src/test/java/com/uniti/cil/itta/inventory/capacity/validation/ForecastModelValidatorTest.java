package com.uniti.cil.itta.inventory.capacity.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.capacity.model.*;
import com.uniti.cil.itta.inventory.capacity.support.ForecastModelFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ForecastModelValidatorTest {

    private final ForecastModelValidator validator = new ForecastModelValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(ForecastModelFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        ForecastModel entity = ForecastModelFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        ForecastModel entity = ForecastModelFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        ForecastModel entity = ForecastModelFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        ForecastModel entity = ForecastModelFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
