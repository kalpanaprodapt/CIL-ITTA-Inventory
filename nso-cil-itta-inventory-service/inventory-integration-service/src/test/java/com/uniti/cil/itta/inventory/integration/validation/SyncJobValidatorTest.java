package com.uniti.cil.itta.inventory.integration.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.integration.model.*;
import com.uniti.cil.itta.inventory.integration.support.SyncJobFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class SyncJobValidatorTest {

    private final SyncJobValidator validator = new SyncJobValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(SyncJobFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        SyncJob entity = SyncJobFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        SyncJob entity = SyncJobFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        SyncJob entity = SyncJobFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        SyncJob entity = SyncJobFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
