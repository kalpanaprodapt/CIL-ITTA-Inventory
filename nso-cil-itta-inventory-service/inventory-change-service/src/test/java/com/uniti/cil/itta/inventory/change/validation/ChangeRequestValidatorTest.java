package com.uniti.cil.itta.inventory.change.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.change.model.*;
import com.uniti.cil.itta.inventory.change.support.ChangeRequestFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ChangeRequestValidatorTest {

    private final ChangeRequestValidator validator = new ChangeRequestValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(ChangeRequestFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        ChangeRequest entity = ChangeRequestFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
