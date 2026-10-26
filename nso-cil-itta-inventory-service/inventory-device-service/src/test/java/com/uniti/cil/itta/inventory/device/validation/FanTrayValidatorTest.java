package com.uniti.cil.itta.inventory.device.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.device.model.*;
import com.uniti.cil.itta.inventory.device.support.FanTrayFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class FanTrayValidatorTest {

    private final FanTrayValidator validator = new FanTrayValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(FanTrayFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        FanTray entity = FanTrayFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        FanTray entity = FanTrayFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        FanTray entity = FanTrayFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        FanTray entity = FanTrayFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
