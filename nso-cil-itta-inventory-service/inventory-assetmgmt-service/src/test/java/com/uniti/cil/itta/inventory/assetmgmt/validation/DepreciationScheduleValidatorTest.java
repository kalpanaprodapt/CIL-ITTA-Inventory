package com.uniti.cil.itta.inventory.assetmgmt.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.assetmgmt.model.*;
import com.uniti.cil.itta.inventory.assetmgmt.support.DepreciationScheduleFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DepreciationScheduleValidatorTest {

    private final DepreciationScheduleValidator validator = new DepreciationScheduleValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(DepreciationScheduleFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        DepreciationSchedule entity = DepreciationScheduleFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
