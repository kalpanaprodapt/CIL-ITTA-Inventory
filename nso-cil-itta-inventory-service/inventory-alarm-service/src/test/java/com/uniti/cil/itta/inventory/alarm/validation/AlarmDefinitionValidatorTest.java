package com.uniti.cil.itta.inventory.alarm.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.alarm.model.*;
import com.uniti.cil.itta.inventory.alarm.support.AlarmDefinitionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class AlarmDefinitionValidatorTest {

    private final AlarmDefinitionValidator validator = new AlarmDefinitionValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(AlarmDefinitionFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        AlarmDefinition entity = AlarmDefinitionFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
