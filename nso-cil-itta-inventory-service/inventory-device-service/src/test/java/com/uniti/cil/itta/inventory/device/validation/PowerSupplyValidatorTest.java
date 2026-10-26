package com.uniti.cil.itta.inventory.device.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.device.model.*;
import com.uniti.cil.itta.inventory.device.support.PowerSupplyFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PowerSupplyValidatorTest {

    private final PowerSupplyValidator validator = new PowerSupplyValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(PowerSupplyFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        PowerSupply entity = PowerSupplyFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
