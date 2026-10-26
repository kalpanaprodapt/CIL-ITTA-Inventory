package com.uniti.cil.itta.inventory.virtual.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.virtual.model.*;
import com.uniti.cil.itta.inventory.virtual.support.HypervisorFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class HypervisorValidatorTest {

    private final HypervisorValidator validator = new HypervisorValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(HypervisorFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        Hypervisor entity = HypervisorFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        Hypervisor entity = HypervisorFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        Hypervisor entity = HypervisorFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        Hypervisor entity = HypervisorFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
