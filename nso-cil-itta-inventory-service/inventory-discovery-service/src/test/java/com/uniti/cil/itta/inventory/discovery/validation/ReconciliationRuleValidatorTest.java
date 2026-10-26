package com.uniti.cil.itta.inventory.discovery.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.discovery.model.*;
import com.uniti.cil.itta.inventory.discovery.support.ReconciliationRuleFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReconciliationRuleValidatorTest {

    private final ReconciliationRuleValidator validator = new ReconciliationRuleValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(ReconciliationRuleFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        ReconciliationRule entity = ReconciliationRuleFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
