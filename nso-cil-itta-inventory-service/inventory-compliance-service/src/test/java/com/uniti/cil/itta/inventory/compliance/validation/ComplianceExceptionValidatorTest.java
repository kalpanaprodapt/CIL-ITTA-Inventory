package com.uniti.cil.itta.inventory.compliance.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.compliance.model.*;
import com.uniti.cil.itta.inventory.compliance.support.ComplianceExceptionFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class ComplianceExceptionValidatorTest {

    private final ComplianceExceptionValidator validator = new ComplianceExceptionValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(ComplianceExceptionFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        ComplianceException entity = ComplianceExceptionFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
