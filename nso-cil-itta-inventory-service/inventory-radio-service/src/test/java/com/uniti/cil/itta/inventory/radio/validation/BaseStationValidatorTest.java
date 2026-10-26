package com.uniti.cil.itta.inventory.radio.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.radio.model.*;
import com.uniti.cil.itta.inventory.radio.support.BaseStationFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class BaseStationValidatorTest {

    private final BaseStationValidator validator = new BaseStationValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(BaseStationFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        BaseStation entity = BaseStationFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        BaseStation entity = BaseStationFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        BaseStation entity = BaseStationFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        BaseStation entity = BaseStationFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
