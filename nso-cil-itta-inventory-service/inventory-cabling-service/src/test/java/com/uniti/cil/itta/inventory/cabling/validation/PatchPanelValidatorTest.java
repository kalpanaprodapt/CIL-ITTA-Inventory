package com.uniti.cil.itta.inventory.cabling.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.uniti.cil.itta.inventory.common.exception.ValidationFailedException;
import com.uniti.cil.itta.inventory.cabling.model.*;
import com.uniti.cil.itta.inventory.cabling.support.PatchPanelFixtures;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;
import java.util.List;
import org.junit.jupiter.api.Test;

class PatchPanelValidatorTest {

    private final PatchPanelValidator validator = new PatchPanelValidator();

    @Test
    void acceptsFixtureEntity() {
        assertThat(validator.collect(PatchPanelFixtures.entity())).isEmpty();
    }

    @Test
    void validateThrowsWithAllMessages() {
        PatchPanel entity = PatchPanelFixtures.entity();
        entity.setName("bad\u0007");
        assertThatThrownBy(() -> validator.validate(entity)).isInstanceOf(ValidationFailedException.class);
    }

    @Test
    void rejectsTooLongCode() {
        PatchPanel entity = PatchPanelFixtures.entity();
        entity.setCode("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("code"));
    }

    @Test
    void rejectsTooLongName() {
        PatchPanel entity = PatchPanelFixtures.entity();
        entity.setName("x".repeat(256));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("name"));
    }

    @Test
    void rejectsTooLongDescription() {
        PatchPanel entity = PatchPanelFixtures.entity();
        entity.setDescription("x".repeat(4001));
        assertThat(validator.collect(entity)).anyMatch(m -> m.contains("description"));
    }
}
