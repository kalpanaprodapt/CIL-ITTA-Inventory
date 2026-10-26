package com.uniti.cil.itta.inventory.integration.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.integration.model.*;
import com.uniti.cil.itta.inventory.integration.support.SyncMappingFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SyncMappingValidatorTest {

    private final SyncMappingValidator validator = new SyncMappingValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(SyncMappingFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        SyncMapping entity = SyncMappingFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        SyncMapping entity = SyncMappingFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        SyncMapping entity = SyncMappingFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        SyncMapping entity = SyncMappingFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
