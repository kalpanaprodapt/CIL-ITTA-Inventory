package com.uniti.cil.itta.inventory.alarm.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.alarm.model.*;
import com.uniti.cil.itta.inventory.alarm.support.AlarmInstanceFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AlarmInstanceValidatorTest {

    private final AlarmInstanceValidator validator = new AlarmInstanceValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(AlarmInstanceFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        AlarmInstance entity = AlarmInstanceFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
