package com.uniti.cil.itta.inventory.discovery.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.discovery.model.*;
import com.uniti.cil.itta.inventory.discovery.support.DiscoveryProfileFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class DiscoveryProfileValidatorTest {

    private final DiscoveryProfileValidator validator = new DiscoveryProfileValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(DiscoveryProfileFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        DiscoveryProfile entity = DiscoveryProfileFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
