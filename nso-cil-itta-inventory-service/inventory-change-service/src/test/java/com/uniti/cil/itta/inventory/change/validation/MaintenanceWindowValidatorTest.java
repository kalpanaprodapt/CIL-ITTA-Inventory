package com.uniti.cil.itta.inventory.change.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.change.model.*;
import com.uniti.cil.itta.inventory.change.support.MaintenanceWindowFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class MaintenanceWindowValidatorTest {

    private final MaintenanceWindowValidator validator = new MaintenanceWindowValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(MaintenanceWindowFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        MaintenanceWindow entity = MaintenanceWindowFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
